#
# Update script db version 39 to db version 40
# $Author: heller $
# $Revision: 1.2 $
#
#Statistic details index missing
#
DROP INDEX IF EXISTS idx_statisticdetails_direction
CREATE INDEX idx_statisticdetails_direction on statisticdetails(direction)
DROP INDEX IF EXISTS idx_statisticdetails_messagestate
CREATE INDEX idx_statisticdetails_messagestate on statisticdetails(messagestate)
DROP INDEX IF EXISTS idx_statisticdetails_mixed
CREATE INDEX idx_statisticdetails_mixed on statisticdetails(messagestate,localstation,partner,direction)


