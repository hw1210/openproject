<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%
Connection conn=null;//db 접속 위한 클래스
PreparedStatement pstmt=null;//질의문 실행시킬 클래스
ResultSet rs=null;//질의문 결과값 들어가는 클래스
int rowcount=0;//row 수 계산
//커넥션 정보
String URL="jdbc:mysql://localhost:3306/caldb?useTimezone=true&serverTimezone=UTC&connectTimeout=3000&useSSL=false"; //db명 포함
String USERID="root";
String USERPW="open96";


String gid=request.getParameter("gid");
String tallday="1";
try{
	Class.forName("com.mysql.cj.jdbc.Driver");//mysql driver 불러옴
	conn=DriverManager.getConnection(URL,USERID,USERPW); //커넥션 얻기
	pstmt=conn.prepareStatement("SELECT tname,tdate FROM todo WHERE tgid=? and tallday=?");//질의문요청
	pstmt.setString(1,gid);
	pstmt.setString(2,tallday);
	rs=pstmt.executeQuery(); //질의문 실행 후 결과 받기
	while(rs.next()){
		//검색 결과 표시
	//	String uid=rs.getString("uid");
	//	String uname=rs.getString("uname");
		//String upnum=rs.getString("upnum");
		
		String tname=rs.getString("tname");
		String tdate=rs.getString("tdate");
		rowcount+=1;
		out.print(tname+"/"+tdate+"&");
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

%>