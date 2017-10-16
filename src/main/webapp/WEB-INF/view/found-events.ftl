<html>
<head><title>Search events page</title>
<body>
<div id="header">
    <H2>
        Search events Page
    </H2>
</div>

<div id="content">
    <div>
        <br/>
        <br/>
        <table style="width:100%">

            <tr>
                <th>Name</th>
                <th>Rate</th>
                <th>Base price</th>
                <th>Date</th>
                <th>Auditorium name</th>
            </tr>

        <#if eventList?size != 0>
            <#list eventList as event>
                <#if event??>

                    <tr>
                        <td>${event.getName()}</td>
                        <td>${event.getRate()}</td>
                        <td>${event.getBasePrice()}</td>
                        <td>${event.getDateTime()}</td>
                        <td>${event.getAuditoriumName()}</td>
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