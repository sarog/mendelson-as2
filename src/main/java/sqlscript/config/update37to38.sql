#
# Update script db version 37 to db version 38
# $Author: heller $
# $Revision: 1.2 $
#
#Add a resend notification
#
ALTER TABLE partner ADD COLUMN algidentprotatt INTEGER DEFAULT 1 NOT NULL