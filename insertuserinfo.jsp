<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%
Connection conn=null;//db 접속 위한 클래스
PreparedStatement pstmt=null;//질의문 실행시킬 클래스
ResultSet rs=null;//질의문 결과값 들어가는 클래스
//커넥션 정보
String URL="jdbc:mysql://localhost:3306/caldb?useTimezone=true&serverTimezone=UTC&connectTimeout=3000&useSSL=false"; //db명 포함
String USERID="root";
String USERPW="open96";

String upnum=request.getParameter("upnum");
//insert into userinfo(upnum) select 'upnum' from dual 
//where not exists(select upnum from userinfo where upnum='upnum')

try{
	Class.forName("com.mysql.cj.jdbc.Driver");//mysql driver 불러옴
	conn=DriverManager.getConnection(URL,USERID,USERPW); //커넥션 얻기
	String sql="insert into userinfo(upnum) value(?)";
	//	String sql="insert into userinfo(upnum) select ? from dual where not select upnum from userinfo where upnum=?";	
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,upnum);
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

out.print(upnum);
%>