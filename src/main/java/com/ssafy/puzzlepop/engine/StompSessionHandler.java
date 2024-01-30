package com.ssafy.puzzlepop.engine;

//public class SessionHandler implements StompSessionHandler {
//
//    private final Set<StompSession> activeSessions = new HashSet<>();
//
//    @Override
//    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//        activeSessions.add(session);
//    }
//
//    @Override
//    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
//
//    }
//
//    @Override
//    public void handleTransportError(StompSession session, Throwable exception) {
//
//    }
//
//    @Override
//    public void afterDisconnected(StompSession session, StompHeaders disconnectedHeaders, CloseStatus closeStatus) {
//        activeSessions.remove(session);
//    }
//
//    // STOMP 세션이 모두 종료되었는지 확인하는 메서드
//    public boolean areAllSessionsClosed() {
//        return activeSessions.isEmpty();
//    }
//
//    // 모든 STOMP 세션을 종료하는 메서드
//    public void closeAllSessions() {
//        for (StompSession session : activeSessions) {
//            session.disconnect();
//        }
//        activeSessions.clear();
//    }
//
//    @Override
//    public Type getPayloadType(StompHeaders headers) {
//        return null;
//    }
//
//    @Override
//    public void handleFrame(StompHeaders headers, Object payload) {
//
//    }
//}
