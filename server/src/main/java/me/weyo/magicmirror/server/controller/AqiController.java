package me.weyo.magicmirror.server.controller;

import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WeYo
 */
public class AqiController extends BaseController {

    /** Serial ID */
    private static final long serialVersionUID = -2640350579706749958L;
    private static final Logger LOG = LoggerFactory.getLogger(AqiController.class);
    
    public void init() throws ServletException {
        this.contentType = "application/json";
    }

    @Override
    public void doLog(String msg, Object object) {
        LOG.debug("AQI handled: " + msg);
    }
}