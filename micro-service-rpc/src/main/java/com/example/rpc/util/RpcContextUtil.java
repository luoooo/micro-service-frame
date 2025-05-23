package com.example.rpc.util;

import org.apache.dubbo.rpc.RpcContext;
import org.springframework.util.StringUtils;

import java.util.Map;

public class RpcContextUtil {

    private static final String TRACE_ID = "traceId";
    private static final String USER_ID = "userId";
    private static final String TENANT_ID = "tenantId";

    /**
     * Set trace ID to RPC context
     *
     * @param traceId trace ID
     */
    public static void setTraceId(String traceId) {
        if (StringUtils.hasText(traceId)) {
            RpcContext.getContext().setAttachment(TRACE_ID, traceId);
        }
    }

    /**
     * Get trace ID from RPC context
     *
     * @return trace ID
     */
    public static String getTraceId() {
        return RpcContext.getContext().getAttachment(TRACE_ID);
    }

    /**
     * Set user ID to RPC context
     *
     * @param userId user ID
     */
    public static void setUserId(String userId) {
        if (StringUtils.hasText(userId)) {
            RpcContext.getContext().setAttachment(USER_ID, userId);
        }
    }

    /**
     * Get user ID from RPC context
     *
     * @return user ID
     */
    public static String getUserId() {
        return RpcContext.getContext().getAttachment(USER_ID);
    }

    /**
     * Set tenant ID to RPC context
     *
     * @param tenantId tenant ID
     */
    public static void setTenantId(String tenantId) {
        if (StringUtils.hasText(tenantId)) {
            RpcContext.getContext().setAttachment(TENANT_ID, tenantId);
        }
    }

    /**
     * Get tenant ID from RPC context
     *
     * @return tenant ID
     */
    public static String getTenantId() {
        return RpcContext.getContext().getAttachment(TENANT_ID);
    }

    /**
     * Get all attachments from RPC context
     *
     * @return attachments map
     */
    public static Map<String, String> getAttachments() {
        return RpcContext.getContext().getAttachments();
    }

    /**
     * Clear RPC context
     */
    public static void clear() {
        RpcContext.getContext().clearAttachments();
    }
} 