#
# Update script db version 43 to db version 44
# Add the possibility to inform the user at connection problems
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE notification ADD COLUMN notifyconnectionproblem INTEGER DEFAULT 0 NOT NULL
