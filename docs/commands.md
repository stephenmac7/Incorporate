## General Company Commands
All commands have the structure /inc &lt;action&gt; [arguments]
#### list
Lists all the companies currently in the game.
Usage: /inc list

#### create
Creates a new company.
Usage: /inc create &lt;company&gt;

#### delete
Delete a company.
Usage: /inc delete &lt;company&gt;
Permission: DELETE

#### rename
Rename a company.
Usage: /inc rename &lt;company&gt; &lt;newName&gt;
Permission: RENAME

## Rank Commands
#### listRanks
List the ranks in a company.
Usage: /inc listRanks &lt;company&gt;
Aliases: lr listranks

#### addRank
Adds a rank to a company.
Usage: /inc addRank &lt;company&gt; &lt;rank&gt; &lt;wage&gt;
Aliases: ar addrank
Permission: MANAGERANKS

#### removeRank
Removes an existing rank from a company.
Usage: /inc removeRank &lt;company&gt; &lt;rank&gt;
Aliases: rr removerank
Permission: MANAGERANKS

#### getWage
Gets teh wage of a rank
Usage: /inc getWage &lt;comapny&gt; &lt;rank&gt;
Aliases: gw getwage

#### setWage
Sets the wage of a rank
Usage: /inc setWage &lt;company&gt; &lt;rank&gt; &lt;wage&gt;
Aliases: sw setwage
Permission: MANAGERANKS

#### getDefaultRank
Gets the default rank of a company. The default rank in the company is the rank of new employees and of employees whose rank has been removed.
Usage: /inc getDefaultRank &lt;company&gt;

#### setDefaultRank
Sets the default rank of a company. Rank must exist.
Usage: /inc setDefaultRank &lt;company&gt; &lt;rank&gt;
Permission: MANAGERANKS

### Permissions
#### grantPerm
Grants a permission to a specific rank.
Usage: /inc grantPerm &lt;company&gt; &lt;rank&gt; &lt;permission&gt;
Aliases: gp grantperm
Permission: MANAGERANKS

#### revokePerm
Revokes a permission from a specific rank.
Usage: /inc revokePerm &lt;company&gt; &lt;rank&gt; &lt;permission&gt;
Aliases: rp revokeperm
Permission: MANAGERANKS

## Employee Commands
#### getRank
Gets an employee's rank
Usage: /inc getRank &lt;company&gt; &lt;employee&gt;
Aliases: gr getrank
Permission: MANAGEEMPLOYEES

#### setRank
Sets an employee's rank
Usage: /inc setRank &lt;company&gt; &lt;employee&gt; &lt;rank&gt;
Aliases: sr setRank
Permission: MANAGEEMPLOYEES

#### fire
Fires an employee
Usage: /inc fire &lt;company&gt; &lt;employee&gt;
Permission: FIRE

## Applicant Commands
#### apply
Apply to a company
Usage: /inc apply &lt;company&gt;

#### reject
Reject an applicant
Usage: /inc reject &lt;company&gt; &lt;applicant&gt;
Permission: HIRE

#### hire
Hire an applicant
Usage: /inc hire &lt;company&gt; &lt;applicant&gt;
Permission: HIRE
