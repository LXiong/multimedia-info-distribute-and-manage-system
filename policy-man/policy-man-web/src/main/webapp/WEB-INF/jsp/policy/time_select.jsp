		<select name="hour">
			<%for(int i = 0; i < 24; i++) {%>
			<option>
			<%if(i<10){ %>
				<%="0"+i %>
			<%}else{ %>
				<%=i %>
			<%} %>
			</option>
			<%} %>
		</select>
		<span>:</span>
		<select name="minute">
			<%for(int i = 0; i < 60; i++) {%>
			<option>
			<%if(i<10){ %>
				<%="0"+i %>
			<%}else{ %>
				<%=i %>
			<%} %>
			</option>
			<%} %>
		</select>