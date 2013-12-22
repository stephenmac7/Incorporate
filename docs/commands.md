# General Company Commands
All commands have the structure /inc &lt;action&gt; [arguments]
#### list
Lists all the companies currently in the game.
Usage: /inc list

#### create
Creates a new company.
Usage: /inc create &lt;company&gt; &lt;console: player&gt;

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
Alias: lr

#### addRank
Adds a rank to a company.
Usage: /inc addRank &lt;company&gt; &lt;rank&gt; &lt;wage&gt;
Alias: ar
Permission: MANAGERANKS

#### removeRank
Removes an existing rank from a company.
Usage: /inc removeRank &lt;company&gt; &lt;rank&gt;
Alias: rr
Permission: MANAGERANKS

#### getWage
Gets teh wage of a rank
Usage: /inc getWage &lt;comapny&gt; &lt;rank&gt;
Alias: gw

#### setWage
Sets the wage of a rank
Usage: /inc setWage &lt;company&gt; &lt;rank&gt; &lt;wage&gt;
Alias: sw
Permission: MANAGERANKS

#### getDRank
Gets the default rank of a company. The default rank in the company is the rank of new employees and of employees whose rank has been removed.
Usage: /inc getDRank &lt;company&gt;

#### setDRank
Sets the default rank of a company. Rank must exist.
Usage: /inc setDRank &lt;company&gt; &lt;rank&gt;
Permission: MANAGERANKS

### Permissions
#### grantPerm
Grants a permission to a specific rank.
Usage: /inc grantPerm &lt;company&gt; &lt;rank&gt; &lt;permission&gt;
Alias: gp
Permission: MANAGERANKS

#### revokePerm
Revokes a permission from a specific rank.
Usage: /inc revokePerm &lt;company&gt; &lt;rank&gt; &lt;permission&gt;
Alias: rp
Permission: MANAGERANKS

#### listPerms
Lists a rank's permissions
Usage: /inc listPerms &lt;company&gt; &lt;rank&gt;
Alias: lp

## Employee Commands
#### getRank
Gets an employee's rank
Usage: /inc getRank &lt;company&gt; &lt;employee&gt;
Alias: gr
Permission: MANAGEEMPLOYEES

#### setRank
Sets an employee's rank
Usage: /inc setRank &lt;company&gt; &lt;employee&gt; &lt;rank&gt;
Alias: sr
Permission: MANAGEEMPLOYEES

#### fire
Fires an employee
Usage: /inc fire &lt;company&gt; &lt;employee&gt;
Permission: FIRE

#### resign
Leave a company
Usage: /inc resign &lt;company&gt; &lt;console: player&gt;

#### employees
List employees
Usage: /inc employees &lt;company&gt;
Alias: empl

## Applicant Commands
#### apply
Apply to a company
Usage: /inc apply &lt;company&gt; &lt;console: player&gt;

#### reject
Reject an applicant
Usage: /inc reject &lt;company&gt; &lt;applicant&gt;
Permission: HIRE

#### hire
Hire an applicant
Usage: /inc hire &lt;company&gt; &lt;applicant&gt;
Permission: HIRE

#### applicants
List applicants
Usage: /inc applicants &lt;company&gt;
Alias: appl
Permission: HIRE

## Money Commands
#### deposit
Deposit money
Usage: /inc deposit &lt;company&gt; &lt;console: player&gt; &lt;amount&gt;
Alias: dp

#### withdraw
Withdraw money
Usage: /inc withdraw &lt;company&gt; &lt;console: player&gt; &lt;amount&gt;
Alias: wd
Permission: WITHDRAW

#### getBalance
Get company balance
Usage: /inc getBalance &lt;company&gt;
Alias: gb
Permission: GETBALANCE
