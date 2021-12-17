#
# Update script db version 35 to db version 36
# $Author: heller $
# $Revision: 1.2 $
#
#Add a resend notification
#
ALTER TABLE notification ADD COLUMN security INTEGER DEFAULT 0 NOT NULL