package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.sql.PlsQlCommands;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;

@ChannelHandler.Sharable
public class DbMessageInsertHandler extends ChannelInboundHandlerAdapter {

    private final DataSource dataSource;

    public DbMessageInsertHandler(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final static Logger logger = LoggerFactory.getLogger(DbMessageInsertHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String message = msg.toString();

        logger.info("'{}'", message);

        if (dataSource == null) {
            return;
        }

        Connection connection = dataSource.getConnection();
        CallableStatement cstmt = connection.prepareCall(PlsQlCommands.insertOlbLogTab);
        //cstmt.setString(1, msg.toString());
        //cstmt.setString(1, message);
        cstmt.setString("p_poruka", String.format("message: **%s**", hexToAscii(message.substring(message.indexOf("ISO") + 3))));
        cstmt.execute();

        cstmt.close();
        connection.close();

        logger.info("message after inserting: '{}' and is of class: {}", message, message.getClass());

//        super.channelRead(ctx, msg);
    }


    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2).toLowerCase();
            output.append((char) Integer.parseInt(str, 16));
        }

        logger.info("hexToAscii output: {}", output.toString());

        return output.toString();
    }


}
