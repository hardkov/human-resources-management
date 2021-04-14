import React, { useEffect } from 'react';
import logo from './logo.svg';
import './App.css';

const formField = (key: any, value: any) => {
  const encodedKey = encodeURIComponent(key);
  const encodedValue = encodeURIComponent(value);
  return `${encodedKey}=${encodedValue}`;
};

function App() {
  useEffect(() => {
    const callApi = async () => {
      const formBody = [];
      formBody.push(formField('username', 'admin'));
      formBody.push(formField('password', 'admin'));
      const response = await fetch(`api/login`, {
        method: 'POST',
        body: formBody.join('&'),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          Accept: 'application/json',
        },
      });

      console.log(response);
    };

    callApi();
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a className="App-link" href="https://reactjs.org" target="_blank" rel="noopener noreferrer">
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
