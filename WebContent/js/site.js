
function checkDate(start, end){
	var s = start.split("-");
	var e = end.split("-");

	var d1 = new Date(s[0], s[1] - 1, s[2]);
	var d2 = new Date(e[0], e[1] - 1, e[2]);

	if(Date.parse(d1) - Date.parse(d2) > 0){
		return false;
	}
	return true; // 正确
}
