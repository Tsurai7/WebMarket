import React from 'react';
import Navbar from './Components/Navbar.jsx';
import AppRouter from './routers/AppRouter.jsx';

function App() {
  return (
    <div>
      <header className="App-header">
        <Navbar/>
      </header>
      <body>
        <AppRouter/>
      </body>
    </div>
  );
}

export default App;