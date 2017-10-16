<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
<head><title>Users page</title>
<body>
<div id="header">
    <H2>
        Users Page
    </H2>
</div>

<div id="content">

    <fieldset>
        <legend>Register User</legend>
        <form name="user" action="registerUser" method="post">
            Login: <input type="text" name="login"/><br/>
            Name: <input type="text" name="name"/><br/>
            Email: <input type="text" name="email"/> <br/>
            Birthday: <input type="date" name="birthday"/><br/>
            Password: <input type="password" name="password"/><br/>
            <input type="submit" value="Register"/>
        </form>
    </fieldset>
    <br/>
<@security.authorize access="hasRole('BOOKING_MANAGER')">
    <div>
        <table style="width:100%">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Birthday</th>
                <th>Role</th>
            </tr>
        <#list model["userList"] as user>
            <#if user??>

                <tr>
                    <td>${user.getName()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getBirthday()}</td>
                    <td>${user.getUserRole()}</td>
                    <td>
                       <form name="user" action="/removeUser" method="post">
                            <input type="hidden" name="id" value="${user.getId()}"/>
                            <input type="hidden" name="name" value="${user.getName()}"/>
                            <input type="hidden" name="email" value="${user.getEmail()}"/>
                            <input type="hidden" name="birthday" value="${user.getBirthday()}"/>
                            <input type="hidden" name="userRole" value="${user.getUserRole()}"/>
                            <button type="submit">Remove</button>
                        </form>
                    </td>

                    <td>
                        <form name="user" action="/refillUserAccount" method="post">
                            <input type="hidden" name="user.id" value="${user.getId()}"/>
                            <input type="hidden" name="user.name" value="${user.getName()}"/>
                            <input type="hidden" name="user.email" value="${user.getEmail()}"/>
                            <input type="hidden" name="user.birthday" value="${user.getBirthday()}"/>
                            <input type="hidden" name="user.userRole" value="${user.getUserRole()}"/>
                            <input type="number" name="prepaidUserMoney"/><br/>
                            <button type="submit">Refill user's account</button>
                        </form>
                    </td>
                    <td>
                        <form name="user" action="/getBookedTickets" method="get">
                            <input type="hidden" name="id" value="${user.getId()}"/>
                            <input type="hidden" name="name" value="${user.getName()}"/>
                            <input type="hidden" name="email" value="${user.getEmail()}"/>
                            <input type="hidden" name="birthday" value="${user.getBirthday()}"/>
                            <input type="hidden" name="userRole" value="${user.getUserRole()}"/>
                            <button type="submit">Get booked tickets</button>
                        </form>
                    </td>
                    <td>
                        <form name="user" action="/getTicketsPdfByUser" method="get">
                            <input type="hidden" name="id" value="${user.getId()}"/>
                            <input type="hidden" name="name" value="${user.getName()}"/>
                            <input type="hidden" name="email" value="${user.getEmail()}"/>
                            <input type="hidden" name="birthday" value="${user.getBirthday()}"/>
                            <input type="hidden" name="userRole" value="${user.getUserRole()}"/>
                            <button type="submit">Get booked tickets PDF</button>
                        </form>
                    </td>

                </tr>

            <#else>
            </#if>
        </#list>

        </table>
        <br/>
        <br/>
        <table style="width:100%">
            <tr>
                <th>User Name</th>
                <th>Prepared money</th>
            </tr>
       <#list model["accountList"] as userAcount>
           <#if userAcount??>
           <tr>
               <td>${userAcount.getUser().getName()}</td>
               <td>${userAcount.getPrepaidUserMoney()}</td>
           </tr>
           <#else>
           </#if>
       </#list>
        </table>

        <br/>
        <br/>
        <table style="width:50%">
            <tr>
                <th>Search user by name:</th>
                <th>Search user by email:</th>
            </tr>
            <tr>
                <td>
                    <form name="user" action="userByName" method="get">
                        <input type="text" name="name"/><br/>
                        <input type="submit" value="Get by name"/>
                    </form>
                </td>
                <td>
                    <form name="user" action="userByEmail" method="get">
                        <input type="text" name="email"/> <br/>
                        <input type="submit" value="Get by email"/>
                    </form>
                </td>
            </tr>
        </table>
        <table>

        <#if message??>${message}<#else></#if>
            </tr>
        </table>

    </div>

</@security.authorize>
</body>
</html>