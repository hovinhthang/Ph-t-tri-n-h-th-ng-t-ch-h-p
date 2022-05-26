--a. Tạo các login; tạo các user khai thác CSDL AdventureWorks2008R2 cho các nhân viên (tên login trùng tên user) (1đ).
CREATE LOGIN HO WITH PASSWORD = '12345',
DEFAULT_DATABASE = AdventureWorks2012
CREATE LOGIN THANG WITH PASSWORD = '12345',
DEFAULT_DATABASE = AdventureWorks2012
CREATE LOGIN QL WITH PASSWORD = '12345',
DEFAULT_DATABASE = AdventureWorks2012

USE AdventureWorks2012
CREATE USER HO FOR LOGIN HO
CREATE USER THANG FOR LOGIN THANG
CREATE USER QL FOR LOGIN QL

--b.Tạo role NhanVien, phân quyền cho role, thêm các user HO, TEN, QL vào các role theo
--phân công ở trên để các nhân viên hoàn thành nhiệm vụ (1đ).
Create ROLE NhanVien
GRANT INSERT,DELETE,UPDATE,SELECT ON [Purchasing].[PurchaseOrderDetail] TO NhanVien

EXEC sp_addrolemember NhanVien,HO
EXEC sp_addrolemember NhanVien,THANG
EXEC sp_addrolemember db_datareader,QL
--c
--d.Ai có thể xem dữ liệu bảng Purchasing.Vendor? Giải thích. Viết lệnh kiểm tra quyền trên
--cửa sổ query của user tương ứng (1đ).

-- QL xem xem dữ liệu bảng Purchasing.Vendor. Vì QL thuộc role db_datareader. NGUYEN va SON không 
--xem được vì no chỉ phân quyên trên bảng [Purchasing].[PurchaseOrderDetail]


--e.Các nhân viên HO, TEN, QL hoàn thành dự án, admin thu hồi quyền đã cấp. Xóa role
--NhanVien. (1đ).
	EXEC sp_droprolemember NhanVien,HO
	EXEC sp_droprolemember NhanVien,THANG
	EXEC sp_droprolemember db_datareader,QL
	-- XOÁ ROLE NHANVIEN
	DROP ROLE NhanVien






HO.sql
select * From [Purchasing].[PurchaseOrderDetail]
WHERE PurchaseOrderDetailID = 711

UPDATE [Purchasing].[PurchaseOrderDetail]
SET ModifiedDate = getdate()
WHERE PurchaseOrderDetailID = 711


select * From [Purchasing].[Vendor]

THANG.sql

--19531711

select * FROM [Purchasing].[PurchaseOrderDetail]
where PurchaseOrderDetailID = 195

DELETE [Purchasing].[PurchaseOrderDetail]
WHERE PurchaseOrderDetailID = 195



select * From [Purchasing].[Vendor]

QL.sql
--19531711

select * FROM [Purchasing].[PurchaseOrderDetail]
where PurchaseOrderDetailID = 841 or PurchaseOrderDetailID= 195

select * From [Purchasing].[Vendor]


CÂU 2

alter database [AdventureWorks2012]
set recovery full

BEGIN TRANSACTION
		UPDATE HumanResources.EmployeePayHistory SET Rate =Rate + (Rate*0.15)
		WHERE BusinessEntityID IN (SELECT emp.BusinessEntityID FROM HumanResources.EmployeePayHistory emp 
                                                        JOIN HumanResources.EmployeeDepartmentHistory edh ON emp.BusinessEntityID =edh.BusinessEntityID
                                                        JOIN HumanResources.Shift s ON s.ShiftID = edh.ShiftID 
                                                        WHERE s.Name = 'Evening' )

		UPDATE HumanResources.EmployeePayHistory SET Rate =Rate + (Rate*0.25)
		WHERE BusinessEntityID IN (SELECT emp.BusinessEntityID FROM HumanResources.EmployeePayHistory emp 
                                                        JOIN HumanResources.EmployeeDepartmentHistory edh ON emp.BusinessEntityID =edh.BusinessEntityID
                                                        JOIN HumanResources.Shift s ON s.ShiftID = edh.ShiftID 
                                                        WHERE s.Name = 'Night' )
		COMMIT
		go

SELECT *FROM HumanResources.EmployeePayHistory
--BusinessEntityID = 62, rate 25

--sau transaction BusinessEntityID = 62, rate 28.75

BACKUP database [AdventureWorks2012]
TO DISK = 'D:\backup\advback2012.bak'
WITH DESCRIPTION = 'Adventurework2012 Full Backup'


--b. Xóa mọi bản ghi trong bảng SalesTerritoryHistory. [Viết lệnh Differential Backup] (1đ)

DELETE  Sales.SalesTerritoryHistory

BACKUP DATABASE [AdventureWorks2012]
TO DISK = 'D:\backup\advback2012.bak'
WITH DIFFERENTIAL,DESCRIPTION ='Adventurework2012 Diff Backup 1'

--c. Bổ sung thêm 1 số phone mới (Person.PersonPhone) tùy ý cho nhân viên có mã số nhân
--viên (BusinessEntityID) là 4 ký tự cuối của Mã SV của chính SV dự thi,
--ModifiedDate=getdate() . [Ghi nhận dữ liệu đang có và Viết lệnh Log Backup] (1đ)
select *from Person.PersonPhone
where BusinessEntityID = 9841

insert Person.PersonPhone values(9841, 123-444-5555,1,GETDATE())


BACKUP Log [AdventureWorks2008R2]
TO DISK ='D:\backup\advback2012.bak'
WITH DESCRIPTION = 'Adventurework2012 Log Backup';	




--d.Xóa CSDL AdventureWorks2008R2. Phục hồi CSDL về trạng thái sau khi thực hiện bước c
--Kiểm tra xem dữ liệu phục hồi có đạt yêu cầu không (lương có tăng, các bản ghi có bị
--xóa, có thêm số phone mới)? (2đ)



RESTORE HEADERONLY FROM DISK='D:\backup\advback2012.bak'
use master

drop database [AdventureWorks2012]

RESTORE DATABASE [AdventureWorks2012]
FROM DISK ='D:\backup\advback2012.bak'
WITH FILE = 1,NORECOVERY

RESTORE DATABASE [AdventureWorks2012]
FROM DISK ='D:\backup\advback2012.bak'
WITH FILE = 2,NORECOVERY

RESTORE DATABASE [AdventureWorks2012]
FROM DISK ='D:\backup\advback2012.bak'
WITH FILE = 3,RECOVERY

go


SELECT * FROM HumanResources.EmployeePayHistory AS emp 
JOIN HumanResources.EmployeeDepartmentHistory AS edh ON emp.BusinessEntityID =edh.BusinessEntityID
JOIN HumanResources.Shift AS s ON s.ShiftID = edh.ShiftID 
WHERE s.Name = 'Evening'

--Lương có tăng


select * From Sales.SalesTerritoryHistory

--Bảng bị xóa

select *from Person.PersonPhone
where BusinessEntityID = 9841

--có chèn được vào bảng



Triggeer update
alter trigger [Production].[Cau3]
	on Production.ProductReview
after update as
begin
	if update(comments)
	begin
		if exists(select * from Production.Product where ProductID = (select ProductID from inserted))
		begin
			select p.ProductID, Color, StandardCost, Rating, Comments from Production.Product as p join inserted i
				on p.ProductID = i.ProductID
			where p.ProductID = i.ProductID
		end
		if not exists(select * from Production.Product
			where ProductID = (select ProductID from inserted))
		begin
			print(N'Mã sản phẩm không tồn tại')
			rollback
		end
	end
end
--thực thi
select * from Production.Product 
select * from [Production].[ProductReview]
update [Production].[ProductReview]
set Comments = N'Co san pham ko'
where [ProductID]=700
drop trigger [Cau3]
Trigger insert
go
create trigger cau3 on [Production].[ProductReview]
after insert 
as
	declare @ProductID int
	select @ProductID = i.ProductID from inserted i
	if EXISTS (select ProductID from inserted where ProductID = @ProductID)
		begin
			SELECT Production.Product.ProductID, Production.Product.Color, 
			Production.Product.StandardCost, Production.ProductReview.Rating, Production.ProductReview.Comments
			FROM   Production.Product INNER JOIN
				   Production.ProductReview ON Production.Product.ProductID = Production.ProductReview.ProductID
			where  Production.Product.ProductID= @ProductID
		end
	else 
		begin
				print 'khong co ma san pham nay'
				rollback
		end

-- Thuc Thi

INSERT [Production].[ProductReview] VALUES (709,'review2','08/08/2006','binhluan2',5, 'san pham nay con khong ','08/08/2006');

INSERT [Production].[ProductReview] VALUES (97,'review2','08/08/2006','binhluan2',4, 'san pham nay con khong ','08/08/2006');

Câu 2: d2
begin tran Cau2a
	update HumanResources.EmployeePayHistory
	set Rate=Rate+Rate*0.2
	where BusinessEntityID 
		in (select e.BusinessEntityID from HumanResources.EmployeePayHistory e
	join HumanResources.EmployeeDepartmentHistory h on e.BusinessEntityID=h.BusinessEntityID
	join HumanResources.Department d on h.DepartmentID=d.DepartmentID
	where GroupName='Executive General and Administration')


	update HumanResources.EmployeePayHistory
	set Rate=Rate+Rate*0.15 where
	BusinessEntityID not in (select e.BusinessEntityID from HumanResources.EmployeePayHistory e
	join HumanResources.EmployeeDepartmentHistory h on e.BusinessEntityID=h.BusinessEntityID
	join HumanResources.Department d on h.DepartmentID=d.DepartmentID
	where GroupName='Executive General and Administration'
	)
commit tran

select * from [HumanResources].[Department]
select * from [HumanResources].[EmployeeDepartmentHistory]
select * from [HumanResources].[EmployeePayHistory]

select * from HumanResources.EmployeePayHistory e
	join HumanResources.EmployeeDepartmentHistory h on e.BusinessEntityID=h.BusinessEntityID
	join HumanResources.Department d on h.DepartmentID=d.DepartmentID 
		where Name='Production' or Name='Production Control'

