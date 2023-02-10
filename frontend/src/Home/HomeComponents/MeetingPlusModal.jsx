import Modal from '../../CommonComponents/Modal/Modal';
import styled from 'styled-components';
import React from 'react';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { APIroot } from '../../Store';
import { useRecoilValue } from 'recoil';

const MeetingPlusModal = ({ onClose }) => {
  const title = 'Meeting Create';
  const API = useRecoilValue(APIroot);

  const [groupList, setGroupList] = useState([]);
  const [conferenceUrl, setConferenceUrl] = useState();

  // 유저가 가입한 그룹 리스트 가져오기
  useEffect(() => {
    axios({
      method: 'POST',
      url: `${API}/conference/click`,
      headers: {
        'Content-Type': 'application/json',
        AccessToken: `${localStorage.getItem('ACCESS_TOKEN')}`,
      },
    }).then((response) => {
      console.log(response);
      setGroupList(response.data.list);
      setConferenceUrl(response.data.url)
      console.log(groupList);
    });
  }, []);

  const CopyHandler = (text) => {
    try {
      navigator.clipboard.writeText(text);
      alert('클립보드 복사완료');
    } catch (error) {
      alert('복사 실패');
    }
  };

  const CancelHandler =() => {
    onClose?.()
  }

  console.log(conferenceUrl);
  return (
    <Modal onClose={onClose} ModalTitle={title}>
      <TitleStyle>
        <div className="name">회의명 </div>
        <div className="nickname">
          <input type="text" style={{ border: 'solid 2px grey' }} />
        </div>
      </TitleStyle>
      <TitleStyle>
        <div className="name">회의 개요 </div>
        <div className="nickname">
          <input type="text" style={{ border: 'solid 2px grey' }} />
        </div>
      </TitleStyle>
      {/* 여기 그룹다운으로 그룹 선택 만들어야됨*/}
      <TitleStyle>
        <div className="name">그룹 선택</div>
        <select name="nickname" style={{ border: 'solid 2px grey' }}>
          {groupList.map((title, i) => {
            console.log(title);
            return (
              <option key={i} value={`${title}`}>
                {title}
              </option>
            );
          })}
        </select>
      </TitleStyle>
      <TitleStyle>
        <div className="name">URL</div>
        <div className="nickname">
          <input type="text" defaultValue={conferenceUrl} style={{ border: 'solid 2px grey' }} />
        </div>
        <ButtonStyle>
          <button onClick={() => CopyHandler(conferenceUrl)}>Copy</button>
        </ButtonStyle>
      </TitleStyle>
      <TitleStyle>
        <div className="name">캡처 허용</div>
        <CheckBoxStyle className="nickname">
          <input type="checkbox" />
          불가능
        </CheckBoxStyle>
      </TitleStyle>
      <TitleStyle>
        <div className="name">화면 공유 허용</div>
        <CheckBoxStyle className="nickname">
          <input type="checkbox" />
          불가능
        </CheckBoxStyle>
      </TitleStyle>
      <TitleStyle>
        <CreateCancelButtonStyle>
          <button>Create</button>
        </CreateCancelButtonStyle>
        <CreateCancelButtonStyle>
          <button onClick={CancelHandler}>Cancel</button>
        </CreateCancelButtonStyle>
      </TitleStyle>
    </Modal>
  );
};

export default MeetingPlusModal;

const TitleStyle = styled.div`
  display: flex;
  flex-direction: row;
  align-items: end; // 세로 기준 맨 아래
  height: 6vh;
  .name {
    display: flex;
    margin-right: 5px;
    width: 30%;
  }
  .nickname {
    display: flex;
    width: 60%;
  }
`;
const CheckBoxStyle = styled.div`
  display: flex;
  align-items: center;
  input {
    width: 20px;
  }
`;

const ButtonStyle = styled.div`
  display: flex;
  width: 20px;
  height: 25px;
  margin-left: 5px;
`;

const CreateCancelButtonStyle = styled.div`
  /* align-items: center; */
  margin-left: 50px;
  padding-left: 50px;
  width: 5%;
  height: 25px;
`;
