<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
<head><title>Auditoriums page</title>
<body>
<div id="header">
    <H2>
        Auditoriums Page
    </H2>
</div>

<@security.authorize  access="hasRole('BOOKING_MANAGER')">
<div id="content">
    <fieldset>
        <legend>Add Auditorium</legend>
        <form name="event" action="createAuditorium" method="post">
            Name: <input type="text" name="name"/><br/>
            Seats Number: <input type="number" name="seatsNumber"/> <br/>
            Vip seats: <input type="text" name="vipSeats"/><br/>
              <input type="submit" value="Save"/>
        </form>
    </fieldset></@security.authorize>


      <div>
        <table style="width:100%">
            <tr>
                <th>Name</th>
                <th>Seats number</th>
                <th>VIP seats</th>
            </tr>
        <#list auditoriumList as auditorium>
            <#if auditorium??>

                <tr>
                    <td>${auditorium.getName()}</td>
                    <td>${auditorium.getSeatsNumber()}</td>
                    <td>${auditorium.getVipSeats()}</td>
                <@security.authorize  access="hasRole('BOOKING_MANAGER')">
                    <td>
                        <form name="event" action="/removeAuditorium" method="post">
                            <input type="hidden" name="id" value="${auditorium.getId()}"/>
                            <input type="hidden" name="name" value="${auditorium.getName()}"/>
                            <input type="hidden" name="seatsNumber" value="${auditorium.getSeatsNumber()}"/>
                            <input type="hidden" name="vipSeats" value="${auditorium.getVipSeats()}"/>
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </@security.authorize>

                </tr>

            <#else>
            </#if>
        </#list>

        </table>
        <br/>
        <br/>

        <br/>
        <br/>
        <table style="width:50%">
            <tr>
                <th>Search auditorium by name:</th>
            </tr>
            <tr>
                <td>
                    <form name="auditorium" action="auditoriumsByName" method="get">
                        <input type="text" name="name"/><br/>
                        <input type="submit" value="Get by name"/>
                    </form>
                </td>
            </tr>
        </table>
        <table>

        <#if message??>${message}<#else></#if>
            </tr>
        </table>

    </div>
</body>
</html>