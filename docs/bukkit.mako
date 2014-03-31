<img src="http://i.imgur.com/1iBURKH.png" alt="mcorp"></a>
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

<h1>Installation</h1>
<p>Development builds of this project can be acquired at the provided continuous integration server. 
These builds have not been approved by the BukkitDev staff. Use them at your own risk.</p>

<p>You can either download a <a href="http://dev.bukkit.org/bukkit-plugins/incorporate/files/">released version</a> or a <a href="https://stephenmac7.ci.cloudbees.com/job/Incorporate/">development build</a>. To use this plugin you'll need:</p>
<ul>
    <li>MongoDB</li>
    <li>Vault</li>
    <li><a href="http://mml.stephenmac.com/static/archives/libs.zip">These libraries</a></li>
</ul>
<p>Start your Mongo server, unzip those libraries into lib/ (create it if you have not done so yet), setup your economy plugin, and you'll be ready to have an intricate economy!</p>

<h1>Commands</h1>
% for category, commands in data.items():
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

<h1>Configuration</h1>
Information about the configuration file can be found in the file itself.

<h1>Future Plans</h1>
These are things that I may add, although not necessarily in the near future.
<ul>
    <li>Semi-Realistic Stock Market</li>
    <li>Optional Automatic Pricing (Using the laws of supply and demand)</li>
    <li>Alerts (New applicant, Price change, You've been fired, etc)</li>
    <li>Taxes (probably not a good idea)</li>
    <li>Using strings instead of IDs, probably by the 1.7.4 release</li>
</ul>
