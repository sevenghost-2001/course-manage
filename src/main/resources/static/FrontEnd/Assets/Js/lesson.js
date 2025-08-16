
/* ======== Lessons / Sections ======== */
const sections = [
  { id:1, title:"Section 1: Introduction & Basics of Asp.Net MVC",
    lessons:[
      { id:1, title:"Course Introduction", duration:"10min", completed:false, video:"https://www.w3schools.com/html/mov_bbb.mp4" },
      { id:2, title:"Intro to ASP.NET MVC & Understanding MVC Architecture", duration:"10min", completed:false, video:"https://www.w3schools.com/html/movie.mp4" },
      { id:3, title:"Intro to Controllers", duration:"2min", completed:false, video:"https://www.w3schools.com/html/mov_bbb.mp4" },
      { id:4, title:"Intro to Action Methods", duration:"1min", completed:false, video:"https://www.w3schools.com/html/movie.mp4" }
    ]},
  { id:2, title:"Section 2: Additional Basics of Asp.Net MVC",
    lessons:[
      { id:5, title:"Where to Start Practical", duration:"1min", completed:false, video:"https://www.w3schools.com/html/movie.mp4" },
      { id:6, title:"Where to download the Source Code", duration:"1min", completed:false, video:"https://www.w3schools.com/html/mov_bbb.mp4" },
      { id:7, title:"Asp.Net Mvc (vs) Asp.Net Web Forms", duration:"6min", completed:false, video:"https://www.w3schools.com/html/mov_bbb.mp4" }
    ]}
];

const courseContentEl = document.getElementById('course-content');
const videoPlayer = document.getElementById('lessonVideo');
const lessonTitle  = document.getElementById('lessonTitle');
let currentLesson = null;

function renderCourseContent() {
  courseContentEl.innerHTML = '';
  sections.forEach((section, idx) => {
    const sectionId = `section-${section.id}`;
    const done = section.lessons.filter(l=>l.completed).length;

    courseContentEl.insertAdjacentHTML('beforeend', `
      <div class="accordion-item card mb-2 bg-white text-drak">
        <h2 class="accordion-header">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" 
                  data-bs-target="#${sectionId}">
            ${section.title} (${done}/${section.lessons.length})
          </button>
        </h2>
        <div id="${sectionId}" class="collapse">
          <div class="card-body p-0">
            ${section.lessons.map((ls,idx)=>`
              <div class="lesson-item ${ls.completed?'lesson-completed':'lesson-pending'} ${idx>0 && !section.lessons[idx-1].completed?'locked':''}"
                   data-id="${ls.id}" data-title="${ls.title}" data-video="${ls.video}">
                <span>
                  ${ls.completed?'✅':(idx>0 && !section.lessons[idx-1].completed?'🔒':'⬜')} 
                  ${ls.title}
                </span>
                <span>${ls.duration}</span>
              </div>`).join('')}
          </div>
          </div>
        </div>
      </div>
    `);
  });

  // click to play
  document.querySelectorAll('.lesson-item').forEach(it=>{
    it.addEventListener('click', ()=>{
      if(it.classList.contains('locked')) return; // chặn nếu bị khóa
      document.querySelectorAll('.lesson-item').forEach(i=>i.classList.remove('active-lesson'));
      it.classList.add('active-lesson');
      const title = it.dataset.title;
      const video = it.dataset.video;
      videoPlayer.src = video;
      lessonTitle.textContent = "Bài học: " + title;

      sections.forEach(s=>s.lessons.forEach(l=>{
        if(l.title === title) currentLesson = l;
      }));
    });
  });
}
// Khi xem xong video → đánh completed + mở khóa bài sau
videoPlayer.addEventListener('ended', ()=>{
  if(currentLesson){
    currentLesson.completed = true;

    // update giao diện bài hiện tại
    const el = document.querySelector(`.lesson-item[data-id="${currentLesson.id}"]`);
    if(el){
      el.classList.remove('lesson-pending');
      el.classList.add('lesson-completed');
      el.querySelector('span').innerText = '✅ ' + currentLesson.title;
    }

    // mở khóa bài tiếp theo trong cùng section
    for(let sec of sections){
      const idx = sec.lessons.findIndex(l=>l.id === currentLesson.id);
      if(idx>-1 && idx<sec.lessons.length-1){
        const nextLesson = sec.lessons[idx+1];
        const nextEl = document.querySelector(`.lesson-item[data-id="${nextLesson.id}"]`);
        if(nextEl){
          nextEl.classList.remove('locked');
          if(!nextLesson.completed){
            nextEl.querySelector('span').innerText = '⬜ ' + nextLesson.title;
          }
        }
      }
    }

    // update số done/total
    sections.forEach(section=>{
      if(section.lessons.some(l=>l.id === currentLesson.id)){
        const done = section.lessons.filter(l=>l.completed).length;
        const headerBtn = document.querySelector(`[data-bs-target="#section-${section.id}"]`);
        if(headerBtn){
          headerBtn.innerHTML = `${section.title} (${done}/${section.lessons.length})`;
        }
      }
    });
  }
});
renderCourseContent();

/* ======== Tabs ======== */
document.querySelectorAll('.tab-btn').forEach(btn=>{
  btn.addEventListener('click', ()=>{
    document.querySelectorAll('.tab-btn').forEach(b=>b.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(c=>c.classList.remove('active'));
    btn.classList.add('active');
    document.getElementById(btn.dataset.tab).classList.add('active');
  });
});

/* ======== Q&A ======== */
let qna = [
  { id:1, title:"Hi, cannot proceed", content:"Please assist", userName:"Anita",
    comments:[{userName:"Instructor", content:"Try cleaning build and re-run", replies:[]}] }
];

const questionList = document.getElementById('questionList');
const questionInput = document.getElementById('questionInput');
document.getElementById('postQuestionBtn').addEventListener('click', addQuestion);

function getTotalComments(q){
  let total = 0;
  q.comments.forEach(c=>{
    total++;
    if(c.replies) total += c.replies.length;
  });
  return total;
}

function renderQuestions(){
  questionList.innerHTML = qna.map(q=>`
    <div class="question-item">
      <h6 class="mb-1">${q.title}</h6>
      <p class="mb-1 text-muted">${q.content||''}</p>
      <small class="d-block mb-2">by ${q.userName}</small>
      <button class="btn btn-sm btn-outline-dark" data-action="toggle" data-id="${q.id}">
        💬 ${getTotalComments(q)} Comments
      </button>
      <div id="comments-${q.id}" class="comments-box" style="display:none"></div>
    </div>
  `).join('');

  questionList.querySelectorAll('[data-action="toggle"]').forEach(btn=>{
    btn.addEventListener('click', ()=>{
      const qId = +btn.dataset.id;
      const box = document.getElementById(`comments-${qId}`);
      if(box.style.display==='none'){ renderComments(qId); box.style.display='block'; }
      else { box.style.display='none'; }
    });
  });
}

function renderComments(qId){
  const q = qna.find(x=>x.id===qId);
  const box = document.getElementById(`comments-${qId}`);
  box.innerHTML = q.comments.map((c,ci)=>`
    <div class="comment-item">
      <b>${c.userName}</b>: ${c.content}
      <div class="ms-3">
        ${c.replies?.map(r=>`
          <div class="reply-item"><b>${r.userName}</b>: ${r.content}</div>
        `).join('')||''}
        <textarea id="replyInput-${qId}-${ci}" placeholder="Write a reply..."></textarea>
        <button class="btn btn-sm btn-secondary mt-1" data-action="reply" data-qid="${qId}" data-cid="${ci}">Reply</button>
      </div>
    </div>
  `).join('') + `
    <textarea id="commentInput-${qId}" placeholder="Write a comment..."></textarea>
    <button class="btn btn-sm btn-primary mt-2" data-action="comment" data-id="${qId}">Comment</button>
  `;

  // reply
  box.querySelectorAll('[data-action="reply"]').forEach(btn=>{
    btn.addEventListener('click', ()=>{
      const qid = +btn.dataset.qid;
      const cid = +btn.dataset.cid;
      const txt = document.getElementById(`replyInput-${qid}-${cid}`).value.trim();
      if(!txt) return;
      let q = qna.find(x=>x.id===qid);
      q.comments[cid].replies.push({userName:"You", content:txt});
      renderComments(qid);
      document.querySelector(`[data-action="toggle"][data-id="${qid}"]`).innerText = `💬 ${getTotalComments(q)} Comments`;
    });
  });

  // comment
  box.querySelector('[data-action="comment"]').addEventListener('click', ()=>{
    const txt = document.getElementById(`commentInput-${qId}`).value.trim();
    if(!txt) return;
    let q = qna.find(x=>x.id===qId);
    q.comments.push({userName:"You", content:txt, replies:[]});
    renderComments(qId);
    document.querySelector(`[data-action="toggle"][data-id="${qId}"]`).innerText = `💬 ${getTotalComments(q)} Comments`;
  });
}

function addQuestion(){
  const raw = questionInput.value.trim();
  if(!raw) return;
  const [title,...rest] = raw.split('\n');
  qna.unshift({ id:Date.now(), title, content:rest.join(' ').trim(), userName:"You", comments:[] });
  questionInput.value='';
  renderQuestions();
}
renderQuestions();
