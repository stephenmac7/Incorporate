<p>This plugin adds companies to Minecraft. Each company has employees, ranks, and stock. More features coming soon!</p>
<h1>Features</h1>
<ul>
    <li>Ranks</li>
    <li>Command-based Buying/Selling from Companies</li>
    <li>Applications</li>
    <li>Fine Permission Management</li>
    <li>Salaries</li>
    <li>Linked Chests (Restock, Recall, Buy, Sell)</li>
</ul>

% for category, commands in data.items():
<h1>Commands</h1>
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

<h1>Configuration</h1>
Currently, this plugin is not configurable although I am planning on adding some configuration including:
<ul>
    <li>Database Name</li>
    <li>Company Creation Fee (Currently hardcoded at 200)</li>
</ul>

<h1>Future Plans</h1>
These are things that I may add, although not necessarily in the near future.
<ul>
    <li>Stock Market</li>
    <li>Taxes</li>
    <li>Automatic Pricing</li>
    <li>Alerts (New applicant, Price change, You've been fired, etc)</li>
</ul>
