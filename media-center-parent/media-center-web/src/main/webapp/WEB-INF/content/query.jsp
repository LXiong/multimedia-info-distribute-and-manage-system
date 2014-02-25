<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
    %><!-- query conditions -->
<div style="position:relative;width:99%;text-align:left">
<form id="queryForm" method="post" action="stb.action" >
	<ul class="query cf">
		<!--<li><label><fmt:message key="Province" /></label>
			<select id="provinceId" name="provinceId" ><option value=""><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="City" /></label>
			<select id="cityId" name="cityId" ><option value=""><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="District" /></label>
			<select id="districtId" name="districtId" ><option value=""><fmt:message key="Total" /></option></select>
		</li>-->
		<li><label><fmt:message key="top.level" /></label>
			<select id="typeId" name="typeId"><option value="" ><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="second.level" /></label>
			<select id="groupId" name="groupId"><option value="" ><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="Status" /></label>
			<select id="stbStatus" name="stbStatus" >
				<option value="" ><fmt:message key="Total" /></option>
				<option value="online" ><fmt:message key="online" /></option>
				<option value="offline" ><fmt:message key="offline" /></option>
				<option value="pause" ><fmt:message key="pause" /></option>
				<option value="active" ><fmt:message key="active" /></option>
				<option value="nointernet" ><fmt:message key="nointernet" /></option>
				<option value="notinstalled" ><fmt:message key="notinstalled" /></option>
				<%-- <c:forEach var="item" items="${mapStatus }" >
					<c:choose>
						<c:when test="${item.key == stbStatus }">
							<option selected="selected" value="${item.key }" ><fmt:message key="${item.value }" /></option>
						</c:when>
						<c:otherwise>
							<option value="${item.key }" ><fmt:message key="${item.value }" /></option>
						</c:otherwise>
					</c:choose>
				</c:forEach> --%>
			</select>
		</li>
		<li><label>MAC</label>
			<input id="stbMac" name="stbMac" value="${stbMac }" size="15px">
		</li>
	</ul><ul class="query cf">
		<li><label><fmt:message key='PolicyVersion' /></label>
			<input id="activePolicy" name="activePolicy" value="${activePolicy }" size="15px">
		</li>
		<li><label><fmt:message key='shop.no' /></label>
			<input id="shopNo" name="shopNo" value="${shopNo }" size="15px">
		</li>
		<li><label><fmt:message key='shop.name' /></label>
			<input id="shopName" name="shopName" value="${shopName }" size="15px">
		</li>
	</ul>
	<div class="btn" style="position:absolute;top:0px;_top:0px;left:700px">
    	<button class="btn1_64" onclick="getPage(0);"><fmt:message key="Query" /></button>
    	<p style="color: maroon; font-size: 12px; font-style: normal" align="center">
		<fmt:message key="search.tips" /></p>
    </div>
	<input type="hidden" id="page" name="page" value="0" ></input>
</form></div>