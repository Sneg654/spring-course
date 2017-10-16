<html>
<head><title>Search tickets page</title>
<body>
<div id="header">
    <H2>
        Search tickets Page
    </H2>
</div>

<div id="content">
    <div>
        <br/>
        <br/>
        <table style="width:100%">
            <tr>
                <th>Event name</th>
                <th>Date</th>
                <th>Seats</th>
                <th>Price</th>
                <th>User</th>
            </tr>

            <#list ticketList as ticket>
                <#if ticket??>

                    <tr>
                        <td>${ticket.getEvent().getName()}</td>
                        <td>${ticket.getDateTime()}</td>
                        <td>${ticket.getSeats()}</td>
                        <td>${ticket.getPrice()}</td>
                        <td>${ticket.getUser().getName()}</td>
                    </tr>

                <#else>
                    <h2>Nothing was found</h2>
                </#if>
            </#list>

    </div>
    </table>
</div>
</body>
</html>