//Cho phép edit textbox
function editableField(id)
{
	$(id).prop("readonly", false);
	$(id).addClass("editField-Focus");
}

//Không cho phép edit textbox
function readOnlyField(id)
{
	$(id).prop("readonly", true);
	$(id).removeClass("editField-Focus");
}

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
	$(".editField").dblclick(function(){
		editableField(this);
	});
	
	$(".editField").keyup(function(e) {
		//ENTER || ESC
        if((!e.altKey && !e.ctrlKey && !e.shiftKey) && (e.keyCode == 13 || e.keyCode == 27))
		{
			readOnlyField(this);
		}
	});
	
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
	
	$(".lnkSua").click(function() {
		var itemID = parseInt($(this).attr("data-id"));
		var trangthaiID = parseInt($(this).attr("data-trangthai"));
		var txt = document.getElementById("txtTenDanhMuc" + itemID);
		
		if(trangthaiID == 0)//Readonly, click to edit
		{
			editableField(txt);
			$(this).text("Xong");
			$(this).attr("data-trangthai", 1);
		}
		else if(trangthaiID == 1)//Editable, click to close (read only)
		{
			readOnlyField(txt);
			$(this).text("Sửa");
			$(this).attr("data-trangthai", 0);
		}
    });
	
	$(".lnkXoa").click(function() {
        deleteItem(this);
    });
	
});

