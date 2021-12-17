//$Header: /mec_as2/sqlscript/runtime/Update36to37.java 6     5.08.13 11:27 Heller $
package sqlscript.runtime;

import de.mendelson.comm.as2.database.IUpdater;
import de.mendelson.util.ConsoleProgressBar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 *
 * Update the database from version 36 to version 37 - store all timestamps in
 * UTC
 *
 * @author S.Heller
 * @version $Revision: 6 $
 * @since build 162
 */
public class Update36to37 implements IUpdater {

    /**
     * Store if this was a successfully operation
     */
    private boolean success = false;

    /**
     * Return if the update was successfully
     */
    @Override
    public boolean updateWasSuccessfully() {
        return (this.success);
    }

    /**
     * Starts the update process
     */
    @Override
    public void startUpdate(Connection connection) throws Exception {
        Calendar calendarUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Statement statement = connection.createStatement();
        //DROP index
        statement.execute("DROP INDEX idx_messages_initdate IF EXISTS");
        statement.execute("DROP INDEX idx_mdn_initdate IF EXISTS");
        //the message log update may last some time..better add a progress bar
        ResultSet result = statement.executeQuery("SELECT COUNT(1) AS counter FROM messages");
        int lineCount = 0;
        if (result.next()) {
            lineCount = result.getInt("counter");
        }
        result.close();
        if (lineCount > 0) {
            ConsoleProgressBar.print(0f);
        }
        long counter = 0;
        ///update messages timestamps
        result = statement.executeQuery("SELECT * FROM messages");
        while (result.next()) {
            Timestamp timestampInit = result.getTimestamp("initdate");
            if (!result.wasNull()) {
                Timestamp timestampSend = result.getTimestamp("senddate");
                if (!result.wasNull()) {
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE messages SET initdate=?,senddate=? WHERE messageid=?");
                    preparedStatement.setTimestamp(1, timestampInit, calendarUTC);
                    preparedStatement.setTimestamp(2, timestampSend, calendarUTC);
                    preparedStatement.setString(3, result.getString("messageid"));
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }
            counter++;
            if (lineCount > 0) {
                float percent = ((float) counter / (float) lineCount) * 100f;
                ConsoleProgressBar.print(percent);
            }
        }
        result.close();
        statement.execute("ALTER TABLE messages ALTER COLUMN initdate RENAME TO initdateutc");
        statement.execute("ALTER TABLE messages ALTER COLUMN senddate RENAME TO senddateutc");
        result = statement.executeQuery("SELECT COUNT(1) AS counter FROM mdn");
        lineCount = 0;
        if (result.next()) {
            lineCount = result.getInt("counter");
        }
        result.close();
        if (lineCount > 0) {
            System.out.println();
            ConsoleProgressBar.print(0f);
        }
        counter = 0;
        //update mdn timestamps
        result = statement.executeQuery("SELECT * FROM mdn");
        while (result.next()) {
            Timestamp timestamp = result.getTimestamp("initdate");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE mdn SET initdate=? WHERE messageid=?");
            preparedStatement.setTimestamp(1, timestamp, calendarUTC);
            preparedStatement.setString(2, result.getString("messageid"));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            counter++;
            if (lineCount > 0) {
                float percent = ((float) counter / (float) lineCount) * 100f;
                ConsoleProgressBar.print(percent);
            }
        }
        result.close();
        statement.execute("ALTER TABLE mdn ALTER COLUMN initdate RENAME TO initdateutc");
        //the message log update may last some time..better add a progress bar
        result = statement.executeQuery("SELECT COUNT(1) AS counter FROM messagelog");
        lineCount = 0;
        if (result.next()) {
            lineCount = result.getInt("counter");
        }
        result.close();
        if (lineCount > 0) {
            System.out.println();
            ConsoleProgressBar.print(0f);
        }
        counter = 0;
        //update messagelog
        result = statement.executeQuery("SELECT * FROM messagelog");
        while (result.next()) {
            Timestamp timestamp = result.getTimestamp("timestamp");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE messagelog SET timestamp=? WHERE id=?");
            preparedStatement.setTimestamp(1, timestamp, calendarUTC);
            preparedStatement.setInt(2, result.getInt("id"));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            counter++;
            if (lineCount > 0) {
                float percent = ((float) counter / (float) lineCount) * 100f;
                ConsoleProgressBar.print(percent);
            }
        }
        result.close();
        statement.execute("ALTER TABLE messagelog ALTER COLUMN timestamp RENAME TO timestamputc");
        //update statistic overview timestamps
        try {
            result = statement.executeQuery("SELECT * FROM statisticoverview");
            while (result.next()) {
                Timestamp timestamp = result.getTimestamp("resetdate");
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE statisticoverview SET resetdate=? WHERE relationshipid=?");
                preparedStatement.setTimestamp(1, timestamp, calendarUTC);
                preparedStatement.setString(2, result.getString("relationshipid"));
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            result.close();
            statement.execute("ALTER TABLE statisticoverview ALTER COLUMN resetdate RENAME TO resetdateutc");
        } catch (Exception e) {
            //ignore, older versions of the community editon dont have this table
        }
        //recreate all INDEX
        statement.execute("CREATE INDEX idx_messages_initdate ON messages(initdateutc)");
        statement.execute("CREATE INDEX idx_mdn_initdate ON mdn(initdateutc)");
        statement.close();
        this.success = true;
    }
}
