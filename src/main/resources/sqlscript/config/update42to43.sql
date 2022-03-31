#
# Update script db version 42 to db version 43
# SMTP servers do not need any account data - and the mail notification is outbound only
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE notification DROP COLUMN mailaccountname
ALTER TABLE notification DROP COLUMN mailaccountpass