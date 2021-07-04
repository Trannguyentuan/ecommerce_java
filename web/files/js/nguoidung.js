//Thay đổi trạng thái của item
function changeStatus(id, statusCode)
{
	if(statusCode == 1) //Kich hoat
	{
		$(id).prop("src", "files/images/check.png");
		$(id).prop("title", "Kích hoạt");
	}
	else //An
	{
		$(id).prop("src", "files/images/delete.png");
		$(id).prop("title", "Ẩn");
	}
}

//Xoá danh mục
function deleteItem(id)
{
	var itemID = parseInt($(id).attr("data-id"));
	
	if(confirm("Bạn có chắc muốn xoá dòng có ID = " + itemID + " ?"))
	{
		//Xoá danh mục
		//
	}
}


$(document).ready(function() {
	
	$(".imgTrangThai").click(function(){
		var statusCode = parseInt($(this).attr("data-code"));
		
		if(statusCode == 1)
		{
			changeStatus(this, 0);
			$(this).attr("data-code", 0);
		}
		else
		{
			changeStatus(this, 1);
			$(this).attr("data-code", 1);
		}
	});
	
	$(".lnkXoa").click(function() {
        deleteItem(this);
    });
	
	//Kiem tra du lieu form
	$("#frmThemNguoiDungMoi").submit(function(e) {
        var pass = $.trim($("#txtMatKhau").val());
		var confirmpass = $.trim($("#txtXacNhanMatKhau").val());
		
		if(pass != confirmpass)
		{
			alert("Mật khẩu xác nhận không đúng!");
			return false;
		}
    });
	
});

