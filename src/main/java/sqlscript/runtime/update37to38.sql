#
# Update script db version 37 to db version 38
# $Author: heller $
# $Revision: 1.2 $
#
#Add a userdefined id to outbound AS2 messages, allows to track them later using the RPC interface
#
ALTER TABLE messages ADD COLUMN userdefinedid VARCHAR(255)
