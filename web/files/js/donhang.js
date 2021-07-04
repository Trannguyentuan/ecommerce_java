//Thay đổi trạng thái của item
function showDetail(id, statusCode)
{
	if(statusCode == 1) //hide
	{
		$(id).css("display", "block");
	}
	else //show
	{
		$(id).css("display", "none");
	}
}

$(document).ready(function() {
	
	$(".lnkChiTiet").click(function(){
		var statusCode = parseInt($(this).attr("data-trangthai"));
		var id = parseInt($(this).attr("data-id"));
		var element = document.getElementById("chiTietDonHang" + id);
		
		if(statusCode == 1)//hide
		{
			showDetail(element, 0);
			$(this).attr("data-trangthai", 0);
		}
		else//show
		{
			showDetail(element, 1);
			$(this).attr("data-trangthai", 1);
		}
	});
	
});

