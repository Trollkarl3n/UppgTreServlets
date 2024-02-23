<form id="loginForm" action="/login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required value="">
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required value="">
    <label for="user_type">Choose a user type:</label>
        <select id="user_type" name="user_type">
          <option value="student">Student</option>
          <option value="teacher">Teacher</option>
        </select>
    <input type="submit" id="loginSubmit" name="loginSubmit" value="Login">
</form>