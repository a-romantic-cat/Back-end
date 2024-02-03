import './App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import styled from "styled-components";
import Home from "./pages/Home";
import Login from "./pages/Login";
import MyLetterbox from "./pages/MyLetterbox";
import AddressBook from "./pages/AddressBook";
import RomanticLetterbox from "./pages/RomanticLetterbox";
import Store from "./pages/Store";
import MyPage from "./pages/MyPage";
import Check1 from './components/MyLetterbox/Check1';
import Check2 from './components/MyLetterbox/Check2';
import Answer1 from './components/MyLetterbox/Answer1';
import Answer2 from './components/MyLetterbox/Answer2';
import Answer3 from './components/MyLetterbox/Answer3';
import Answer4 from './components/MyLetterbox/Answer4';
import OpenLetter1 from './components/MyLetterbox/OpenLetter1';
import PastLetterbox1 from './components/MyLetterbox/PastLetterbox1';
import PastLetterbox2 from './components/MyLetterbox/PastLetterbox2';
import PastLetterbox3 from './components/MyLetterbox/PastLetterbox3';
import CreateAccount from './components/Login/CreateAccount';
import Terms from './components/Login/Terms';
import MakeLetterbox from './components/Login/MakeLetterbox';
import MissionMain from './components/Store/MissionMain';
import CollectionBoxMain from './components/Store/CollectionBoxMain';
import WritingLetter  from './components/RomanticLetterbox/WritingLetter/WritingLetter';
import CompletedLetterWriting from './components/RomanticLetterbox/WritingLetter/CompletedLetterWriting';
import CompletedLetterReplying from './components/RomanticLetterbox/ReplyingLetter/CompletedLetterReplying';
import ReplyingLetterMain from './components/RomanticLetterbox/ReplyingLetter/ReplyingLetterMain';
import ReplyingLetter from './components/RomanticLetterbox/ReplyingLetter/ReplyingLetter';
import BoxSetting1 from './components/Login/BoxSetting1';
import BoxSetting2 from './components/Login/BoxSetting2';
import BoxSetting3 from './components/Login/BoxSetting3';
import SettingEnd from './components/Login/SettingEnd';

const Background = styled.div`
  background-color: #FFFEF8;
  width: 1920px;
  height: 1080px;
`

function App() {
  return (
    <Background>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/CreateAccount" element={<CreateAccount />} />
          <Route path="/MyLetterbox" element={<MyLetterbox />} />
          <Route path="/AddressBook" element={<AddressBook />} />
          <Route path="/RomanticLetterbox" element={<RomanticLetterbox />} />
          <Route path="/WritingLetter" element={<WritingLetter />} />
          <Route path="/CompletedLetterWriting" element={<CompletedLetterWriting />} />
          <Route path="/CompletedLetterReplying" element={<CompletedLetterReplying />} />
          <Route path="/ReplyingLetterMain" element={<ReplyingLetterMain />} />
          <Route path="/ReplyingLetter" element={<ReplyingLetter />} />
          <Route path="/Store" element={<Store />} />
          <Route path="/MyPage" element={<MyPage />} />
          <Route path="/Check1" element={<Check1 />} />
          <Route path="/Check2" element={<Check2 />} />
          <Route path="/Answer1" element={<Answer1 />} />
          <Route path="/Answer2" element={<Answer2 />} />
          <Route path="/Answer3" element={<Answer3 />} />
          <Route path="/Answer4" element={<Answer4 />} />
          <Route path="/OpenLetter1" element={<OpenLetter1 />} />
          <Route path="/PastLetterbox1" element={<PastLetterbox1 />} />
          <Route path="/PastLetterbox2" element={<PastLetterbox2 />} />
          <Route path="/PastLetterbox3" element={<PastLetterbox3 />} />
          <Route path="/Terms" element={<Terms />} />
          <Route path="/MakeLetterbox" element={<MakeLetterbox />} />
          <Route path="/MissionMain" element={<MissionMain />} />
          <Route path="/CollectionBoxMain" element={<CollectionBoxMain />} />
          <Route path="/BoxSetting1" element={<BoxSetting1 />} />
          <Route path="/BoxSetting2" element={<BoxSetting2 />} />
          <Route path="/BoxSetting3" element={<BoxSetting3 />} />
          <Route path="/SettingEnd" element={<SettingEnd />} />
        </Routes>
      </BrowserRouter>
    </Background>
  );
}

export default App;