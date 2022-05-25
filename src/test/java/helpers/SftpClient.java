package helpers;

import com.jcraft.jsch.*;
import org.testng.Assert;

public class SftpClient {

	private Session session = null;

	public void connect(String remoteHost, int remotePort, String username, String password) throws JSchException {
          JSch jsch = new JSch();

          session = jsch.getSession(username, remoteHost, remotePort);
          session.setPassword(password);

          session.setConfig("StrictHostKeyChecking", "no");
          session.connect();
      }

	public void upload(String source, String destination) throws JSchException, SftpException {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.put(source, destination);
		sftpChannel.exit();
	}

	public void download(String source, String destination) throws JSchException, SftpException {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.get(source, destination);
		sftpChannel.exit();
	}
	
	public boolean isFileOnFtp(String destination) throws JSchException {
		
		boolean fileExists = true;
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;

		try {
			sftpChannel.lstat(destination);
		} catch (SftpException e){
		    if(e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE){
		    // file doesn't exist
		    fileExists = false;
		    } else {
		    // something else went wrong
		        Assert.fail("[ERROR] Error occurred while trying to check if file is on ftp!");
		    }
		}

		sftpChannel.exit();
		return fileExists;
	}

	public void disconnect() {
		if (session != null) {
			session.disconnect();
		}
	}
}