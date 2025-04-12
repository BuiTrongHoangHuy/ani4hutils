import './App.css'
import {BrowserRouter} from "react-router-dom";
import Router from "./router.tsx";
import {ToastContainer} from "react-toastify";

function App() {

  return (
      <main>
          <BrowserRouter>
            <Router/>
              <ToastContainer />
          </BrowserRouter>
      </main>
  )
}

export default App
