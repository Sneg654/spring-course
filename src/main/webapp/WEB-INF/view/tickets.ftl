<html>
<head><title>Tickets page</title>
<body>
<div id="header">
    <H2>
        Tickets Page
    </H2>
</div>

<div id="content">

    <fieldset>
        <legend>Book Ticket</legend>

    </fieldset>
    <br/>

<table style="width:100%">
    <tr>
        <th>Event</th>
        <th>Date</th>
        <th>Seats</th>
        <th>User</th>
        <th>Price</th>
    </tr>
    <tr>

    <#list ticketList as ticket>
        <#if ticket??>

            <tr>
                <td>${ticket.getEvent()}</td>
                <td>${ticket.getDateTime()}</td>
                <td>${ticket.getSeats()}</td>
                <td>${ticket.getUser()}</td>
                <td>${ticket.getPrice()}</td>
                <td>
                    <form name="ticket" action="/book" method="post">
                        <input type="hidden" name="id" value="${ticket.getId()}"/>
                        <input type="hidden" name="event.id" value="${ticket.getEvent().getId()}"/>
                        <input type="hidden" name="dateTime" value="${ticket.getDateTime()}"/>
                        <input type="hidden" name="seats" value="${ticket.getSeats()}"/>
                        <input type="hidden" name="user.id" value="${ticket.getUser().getId()}"/>
                        <input type="hidden" name="price" value="${ticket.getPrice()}"/>
                        <button type="submit">Book Ticket</button>
                    </form>
                </td>

            </tr>

        <#else>
        </#if>
    </#list>

    </tr>
</table>
</div>
</body>
</html>