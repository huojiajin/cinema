package howard.cinema.manage.manage.common;

import howard.cinema.core.dao.entity.acl.SystemInfo;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.SystemInfoMapper;
import howard.cinema.core.manage.common.CommonAbstract;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.manage.manage.tools.MyMecachedPrefix;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.time.LocalDateTime;

/**
 * @name: AbstractManager
 * @description: 通用类Manager
 * @author: huojiajin
 * @time: 2020/5/25 14:53
 */
public abstract class AbstractManager extends CommonAbstract {

    @Autowired
    private SystemInfoMapper systemInfoMapper;
    @Autowired
    protected MemcachedClient memcachedClient;


    protected void addSysLog(String info, String token, String eigenValue) {
        User user = getUser(token);
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setInsertTime(LocalDateTime.now());
        systemInfo.setUserId(user.getId());
        systemInfo.setInfo(info);
        systemInfo.setEigenValue(eigenValue);
        systemInfoMapper.persist(systemInfo);
    }

    protected User getUser(String token){
        String userStr = (String)memcachedClient.get(MyMecachedPrefix.loginTokenPrefix + token);
        User user = null;
        try {
            user = JsonTools.json2Object(userStr, User.class);
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
        return user;
    }

    /**
     * @Name FileToBase64Str
     * @Author HuoJiaJin
     * @Description 文件转Base64字符串
     * @Date 2021/3/24 0:26
     * @Param [file]
     * @Return java.lang.String
     **/
    protected String FileToBase64Str(File file) throws IOException {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            return new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            logger.error("", e);
            throw new IOException("文件转Base64转换失败");
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
    }

    /**
     * @Name inputStreamToBase64Str
     * @Author HuoJiaJin
     * @Description 流转base64字符串
     * @Date 2021/3/24 0:26
     * @Param [is]
     * @Return java.lang.String
     **/
    protected String inputStreamToBase64Str(InputStream is) throws IOException {
        byte[] data = inputStreamToString(is);
        return new String(Base64.encodeBase64(data));
    }

    /**
     * @Name inputStreamToString
     * @Author HuoJiaJin
     * @Description 流转字符串
     * @Date 2021/3/24 0:27
     * @Param [is]
     * @Return byte[]
     **/
    private byte[] inputStreamToString(InputStream is) throws IOException {
        byte[] data = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("", e);
                    throw e;
                }
            }
        }
        return data;
    }
}
