<html>
<head><title> Actions page</title> </head>
<body>
<div id="header">
    <H2>
       Actions page
    </H2>
</div>

<div id="content">

    <fieldset>
        <legend>Work with</legend>
        <table class="buttonsTable">
            <tr>
                <th>
                    <form action="/users" method="get">
                        <button type="submit" class="link">
                             Users cabinet
                        </button>
                    </form>
                </th>

                <th>
                    <form action="/tickets" method="get">
                        <button type="submit" class="link">
                            Booking page
                        </button>
                    </form>
                </th>

                <th>
                    <form action="/events" method="get">
                        <button type="submit" class="link">
                            Events page
                        </button>
                    </form>
                </th>

                <th>
                    <form action="/auditoriums" method="get">
                        <button type="submit" class="link">
                            Auditoriums page
                        </button>
                    </form>
                </th>
                <th>
                    <form action="/uploading-user" method="get">
                        <button type="submit" class="link">
                            Uploading user xml
                        </button>
                    </form>
                </th>
                <th>
                    <form action="/uploading-event" method="get">
                        <button type="submit" class="link">
                            Uploading event xml
                        </button>
                    </form>
                </th>
            </tr>

        </table>
    </fieldset>
    <br/>
    <span class="floatRight"><a href="logout"/>Logout</a></span>

</div>
</body>
</html>