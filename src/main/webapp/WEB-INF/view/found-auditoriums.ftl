<html>
<head><title>Search auditoriums page</title>
<body>
<div id="header">
    <H2>
        Search auditoriums Page
    </H2>
</div>

<div id="content">
    <div>
        <br/>
        <br/>
        <table style="width:100%">

            <tr>
                <th>Name</th>
                <th>Seats number</th>
                <th>VIP seats</th>
            </tr>

        <#if auditoriumList?size != 0>
            <#list auditoriumList as auditorium>
                <#if auditorium??>

                    <tr>
                        <td>${auditorium.getName()}</td>
                        <td>${auditorium.getSeatsNumber()}</td>
                        <td>${auditorium.getVipSeats()}</td>
                    </tr>

                <#else>
                    <h1>nothing was found</h1>
                </#if>
            </#list>
        <#else>
            <h1>nothing was found</h1>
        </#if>
    </div>
    </table>
</div>
</body>
</html>