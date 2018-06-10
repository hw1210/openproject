<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%
Connection conn=null;//db 접속 위한 클래스
PreparedStatement pstmt=null;//질의문 실행시킬 클래스
ResultSet rs=null;//질의문 결과값 들어가는 클래스
request.setCharacterEncoding("UTF-8");

//커넥션 정보
String URL="jdbc:mysql://localhost:3306/caldb?useTimezone=true&serverTimezone=UTC&connectTimeout=3000&useSSL=false"; //db명 포함
String USERID="root";
String USERPW="open96";

String tdday=request.getParameter("dday");
String tname=request.getParameter("title");
String tallday=request.getParameter("allday");
String tdate=request.getParameter("dateandtime");
String upnum=request.getParameter("upnum");
String tuid="";


try{
	Class.forName("com.mysql.cj.jdbc.Driver");//mysql driver 불러옴
	conn=DriverManager.getConnection(URL,USERID,USERPW); //커넥션 얻기
	pstmt=conn.prepareStatement("SELECT uid FROM userinfo WHERE upnum=?");//질의문요청
	pstmt.setString(1,upnum);
	rs=pstmt.executeQuery(); //질의문 실행 후 결과 받기
	while(rs.next()){
		//검색 결과 표시
		tuid=rs.getString("uid");
		//String uname=rs.getString("uname");
		//String upnum=rs.getString("upnum");
		//String uimg=rs.getString("uimg");
		//String utsize=rs.getString("utsize");

	}
}
catch(Exception e){
	e.printStackTrace();
}
finally{
	//사용 후 역순으로 객체 닫기
	if(rs!=null)rs.close();
	if(pstmt!=null)pstmt.close();
	if(conn!=null)conn.close();
}




//insert into userinfo(upnum) select 'upnum' from dual 
//where not exists(select upnum from userinfo where upnum='upnum')
//sMsg="dDay="+strings[0]+"&"+"title="+strings[1]+"&"+"allDay="+strings[2]+"&"+"dateAndTime="+strings[3]+"&"+"group="+strings[4];
            //115dd
try{
	Class.forName("com.mysql.cj.jdbc.Driver");//mysql driver 불러옴
	conn=DriverManager.getConnection(URL,USERID,USERPW); //커넥션 얻기
	String sql="insert into todo(tname,tuid,tallday,tdday,tdate) values(?,?,?,?,?)";

	//	String sql="insert into userinfo(upnum) select ? from dual where not select upnum from userinfo where upnum=?";	
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,tname);
	pstmt.setString(2,tuid);
	pstmt.setString(3,tallday);
	pstmt.setString(4,tdday);
	pstmt.setString(5,tdate);
	out.print(tuid);
	//pstmt.setString(2,upnum);
	pstmt.execute();

}
catch(Exception e){
	e.printStackTrace();
}
finally{
	//사용 후 역순으로 객체 닫기
	if(pstmt!=null)pstmt.close();
	if(conn!=null)conn.close();
}


%>