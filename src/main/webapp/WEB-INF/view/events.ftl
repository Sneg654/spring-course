<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
<head><title>Events page</title>
<body>
<div id="header">
    <H2>
        Events Page
    </H2>
</div>

<div id="content">

<@security.authorize  access="hasRole('BOOKING_MANAGER')">
    <fieldset>
        <legend>Add Event</legend>
        <form name="event" action="createEvent" method="post">
            Name: <input type="text" name="name"/><br/>
            Rate: <input type="text" name="rate"/> <br/>
            Base price: <input type="number" name="basePrice"/><br/>
            Date: <input type="date" name="dateTime"/><br/>
            Auditorium name:  <input list="auditoriums" name="auditoriumName">
            <datalist id="auditoriums">
                <option value="Blue hall">
                <option value="Red hall">
                <option value="Yellow hall">
            </datalist> <br/>
            <input type="submit" value="Save"/>
        </form>
    </fieldset>
    <br/>
</@security.authorize>
    <div>
        <table style="width:100%">
            <tr>
                <th>Name</th>
                <th>Rate</th>
                <th>Base price</th>
                <th>Date</th>
                <th>Auditorium name</th>
            </tr>
        <#list model["eventList"] as event>
            <#if event??>

                <tr>
                    <td>${event.getName()}</td>
                    <td>${event.getRate()}</td>
                    <td>${event.getBasePrice()}</td>
                    <td>${event.getDateTime()}</td>
                     <td>${event.getAuditorium().getName()}</td>
                <@security.authorize  access="hasRole('BOOKING_MANAGER')">
                    <td>
                        <form name="event" action="/removeEvent" method="post">
                            <input type="hidden" name="id" value="${event.getId()}"/>
                            <input type="hidden" name="name" value="${event.getName()}"/>
                            <input type="hidden" name="rate" value="${event.getRate()}"/>
                            <input type="hidden" name="basePrice" value="${event.getBasePrice()}"/>
                            <input type="hidden" name="dateTime" value="${event.getDateTime()}"/>
                            <input type="hidden" name="auditoriumName" value="${event.getAuditorium().getName()}"/>
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </@security.authorize>
                    <td>
                        <form name="event" action="/getTicketsPdfByEvent" method="get">
                            <input type="hidden" name="id" value="${event.getId()}"/>
                            <input type="hidden" name="name" value="${event.getName()}"/>
                            <input type="hidden" name="rate" value="${event.getRate()}"/>
                            <input type="hidden" name="basePrice" value="${event.getBasePrice()}"/>
                            <input type="hidden" name="dateTime" value="${event.getDateTime()}"/>
                            <input type="hidden" name="auditoriumName" value="${event.getAuditorium().getName()}"/>
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

        <br/>
        <br/>
        <table style="width:50%">
            <tr>
                <th>Search event by name:</th>
                <th>Search event by date range:</th>
                <th>Search event from date:</th>
            </tr>
            <tr>
                <td>
                    <form name="user" action="eventsByName" method="get">
                        <input type="text" name="name"/><br/>
                        <input type="submit" value="Get by name"/>
                    </form>
                </td>
                <td>
                    <form name="user" action="eventsByDateRange" method="get">
                        <input type="date" name="fromDate"/> <br/>
                        <input type="date" name="toDate"/> <br/>
                        <input type="submit" value="Get by date range"/>
                    </form>
                </td>
                <td>
                    <form name="user" action="eventsAfterDate" method="get">
                        <input type="date" name="toDate"/> <br/>
                        <input type="submit" value="Get after date"/>
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