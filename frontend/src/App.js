import React, { useState, useEffect, useRef } from 'react';
import Editor from '@monaco-editor/react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import ReviewPanel from './ReviewPanel';
import './App.css';

const DEFAULT_CODE = `public int findMax(int[] arr) {
    int max = 0;
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length; j++) {
            if (arr[i] > max) max = arr[i];
        }
    }
    return max;
}`;

function App() {
  const [code, setCode] = useState(DEFAULT_CODE);
  const [review, setReview] = useState(null);
  const [connected, setConnected] = useState(false);
  const [analyzing, setAnalyzing] = useState(false);
  const stompClient = useRef(null);

  useEffect(() => {
    connect();
    return () => stompClient.current?.deactivate();
  }, []);

  function connect() {
    const client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      onConnect: () => {
        setConnected(true);
        client.subscribe('/topic/feedback', (message) => {
          setReview(JSON.parse(message.body));
          setAnalyzing(false);
        });
      },
      onDisconnect: () => setConnected(false),
    });
    client.activate();
    stompClient.current = client;
  }

  function analyzeCode() {
    if (!stompClient.current?.connected) return;
    setAnalyzing(true);
    setReview(null);
    stompClient.current.publish({
      destination: '/app/review',
      body: JSON.stringify({ code, language: 'java', sessionId: 'session-1' }),
    });
  }

  return (
    <div className="app">
      <header className="header">
        <div className="logo">⚡ CodeLens</div>
        <div className="header-right">
          <span className={`status ${connected ? 'online' : 'offline'}`}>
            {connected ? '● Live' : '○ Disconnected'}
          </span>
          <button className="analyze-btn" onClick={analyzeCode} disabled={!connected || analyzing}>
            {analyzing ? 'Analyzing...' : 'Analyze Code'}
          </button>
        </div>
      </header>

      <div className="main">
        <div className="editor-pane">
          <div className="pane-title">editor.java</div>
          <Editor
            height="100%"
            defaultLanguage="java"
            value={code}
            onChange={(val) => setCode(val || '')}
            theme="vs-dark"
            options={{
              fontSize: 14,
              minimap: { enabled: false },
              scrollBeyondLastLine: false,
              lineNumbers: 'on',
              renderLineHighlight: 'line',
              fontFamily: 'monospace',
            }}
          />
        </div>

        <div className="review-pane">
          <div className="pane-title">ai-review.json</div>
          <ReviewPanel review={review} analyzing={analyzing} />
        </div>
      </div>
    </div>
  );
}

export default App;
