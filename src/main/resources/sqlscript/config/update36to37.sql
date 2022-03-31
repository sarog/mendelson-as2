#
# Update script db version 36 to db version 37
# $Author: heller $
# $Revision: 1.2 $
#
#Add a resend notification
#
ALTER TABLE partner ADD COLUMN partnercontact Object
ALTER TABLE partner ADD COLUMN partneraddress Object