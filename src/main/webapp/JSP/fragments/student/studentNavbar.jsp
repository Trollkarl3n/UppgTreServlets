<navbar id="studentNavbar">
    <nav>
                <form action="/userPage" method="post">
                <select id="user_type" name="courseId">
                    <c:forEach items="${courses}" var="dataPunkt">
                        <option value="${dataPunkt[0]}">${dataPunkt[1]}</option>
                    </c:forEach>
                </select>
                    <input type="submit" id="studentSubmit" name="studentSubmitButton" value="Show Details">
                    <button onclick=location.href='/userPage'>Go Back</button>
                </form>
    </nav>
</navbar>