<html>
    <head>
        <title>Incorporate Commands</title>
        <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="col-lg-12">
                <h1>Incorporate Commands</h1>
            % for category, commands in data.items():
                <hr>
                <h2>${category} Commands</h2>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Usage</th>
                            <th>Alias</th>
                            <th>Permission</th>
                        </tr>
                    </thead>
                    <tbody>
                    % for command in commands:
                        <tr>
                            <td>${command['name']}</td>
                            <td>${command['desc'] | n}</td>
                            <td>/inc ${command['name']} ${command['usage'] if 'usage' in command else '' | n,h}</td>
                            <td>${command['alias'] if 'alias' in command else None}</td>
                            <td>${', '.join(command['permission']) if 'permission' in command else None}</td>
                        </tr>
                    % endfor
                    </tbody>
                </table>
            % endfor
                <small><sup id="note1">1</sup>The default rank is the rank of new employees and of employees whose rank has been removed.</small><br>
                <small><sup id="note2">2</sup>Adding a 'y' argument makes the cleanup more aggressive</small><br>
                <small><sup id="note3">3</sup>More information about linking chests coming soon</small>
            </div>
        </div>
    </body>
</html>
