<html>
<head><title>Search Users page</title>
<body>
<div id="header">
    <H2>
        Search Users Page
    </H2>
</div>

<div id="content">
    <div>
        <br/>
        <br/>
        <table style="width:100%">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Birthday</th>
            </tr>
        <#if userList?size != 0>
            <#list userList as user>
                <#if user??>

                    <tr>
                        <td>${user.getName()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getBirthday()}</td>

                    </tr>

                <#else>
                    <h1>nobody was found</h1>
                </#if>
            </#list>
        <#else>
            <h1>nobody was found</h1>
        </#if>
    </div>
    </table>
</div>
</body>
</html>