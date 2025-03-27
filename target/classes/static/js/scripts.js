console.log("✅ script.js is loaded successfully!");
document.addEventListener("DOMContentLoaded", function () {
    
    const form = document.getElementById("feedbackForm");
    const questionContainer = document.getElementById("question-container");
    const nextButton = document.getElementById("nextButton");
    const prevButton = document.getElementById("prevButton");
    const trackerContainer = document.getElementById("tracker");

    const timerDisplay = document.createElement("div");
    timerDisplay.classList.add("timer", "text-center", "fw-bold", "mt-3");
    document.body.prepend(timerDisplay); 

    let score = parseInt(localStorage.getItem("quizScore")) || 0;
    let currentQuestionIndex = 0;
    let startTime = localStorage.getItem("quizStartTime") ? parseInt(localStorage.getItem("quizStartTime")) : Date.now();
    localStorage.setItem("quizStartTime", startTime);

    function getElapsedTime() {
        let elapsedTime = Math.floor((Date.now() - startTime) / 1000);
        localStorage.setItem("quizTime", elapsedTime);
    }

window.addEventListener("beforeunload", getElapsedTime);


    function updateTimer() {
    let elapsedTime = Math.floor((Date.now() - startTime) / 1000);
    const minutes = Math.floor(elapsedTime / 60);
    const seconds = elapsedTime % 60;
    timerDisplay.innerHTML = `⏳ Time Elapsed: <strong>${minutes} min ${seconds} sec</strong>`;
    localStorage.setItem("quizTimeTaken", elapsedTime);
}

    setInterval(updateTimer, 1000); // Update Timer Every Second

   function updateTracker() {
    trackerContainer.innerHTML = "";
    questions.forEach((q, index) => {
        const savedAnswer = localStorage.getItem(`question_${q.id}_answer`);
        let boxColor = savedAnswer ? "green" : "red";  

        const box = document.createElement("div");
        box.classList.add("tracker-box", boxColor);
        box.textContent = index + 1; 
        trackerContainer.appendChild(box);
    });
}

    
    function loadQuestion(index) {
        console.log("✅ Loading Question:", questions[index]);
        const question = questions[index];
        if (!question) return;

        questionContainer.innerHTML = `
            <h2>Q.No.${question.id}</h2>
            <div class="d-flex justify-content-center align-items-center gap-4">
                <img src="${question.image1}" class="img-fluid" alt="Example 1" style="max-width: 150px;">
                <h3>Is Rotated To</h3>
                <img src="${question.image2}" class="img-fluid" alt="Example 2" style="max-width: 150px;">
            </div>
            <h3 class="mt-3">As</h3>
            <img src="${question.image3}" class="img-fluid" alt="Example 3" style="max-width: 150px;">
            <h3>is Rotated To</h3>
            <div class="d-flex justify-content-center gap-4 flex-wrap">
                ${question.options.map(option => `
                    <label class="text-center">
                        <input type="radio" name="Option" value="${option.label}">
                        <br> <img src="${option.image}" class="img-fluid" alt="${option.label}" style="max-width: 120px;">
                        <br> ${option.label}
                    </label>
                `).join('')}
            </div>
            <div class="mt-3">
                <button type="button" id="clearButton" class="btn btn-warning">Clear Response</button>
            </div>
        `;

        const savedAnswer = localStorage.getItem(`question_${question.id}_answer`);
        if (savedAnswer) {
            document.querySelector(`input[value="${savedAnswer}"]`).checked = true;
        }

        prevButton.disabled = index === 0;
        nextButton.disabled = index === questions.length - 1;

        updateTracker();

        document.getElementById("clearButton").addEventListener("click", function () {
            localStorage.removeItem(`question_${question.id}_answer`);
            document.querySelectorAll('input[name="Option"]').forEach(input => input.checked = false);
            updateTracker();
        });
    }

    loadQuestion(currentQuestionIndex);

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        const selectedOption = document.querySelector('input[name="Option"]:checked');
        if (!selectedOption) {
            alert("Please select an option before saving.");
            return;
        } 

        const question = questions[currentQuestionIndex];
        const userAnswer = selectedOption.value;
        console.log("Sending response:", { questionId: question.id, selectedOption: userAnswer });
        fetch("http://localhost:5000/save-response", { 
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({ questionId: question.id, selectedOption: userAnswer })
        })
        
        .then(response => response.json())
        .then(data => {
            console.log("✅ Response saved successfully:", data);
        })
        .catch(error => console.error("❌ Error saving response:", error));

        localStorage.setItem(`question_${question.id}_answer`, userAnswer);
        updateTracker();

        if (userAnswer === question.correctAnswer) {
            score++;
        }

        if (currentQuestionIndex === questions.length - 1) {
            const startTime = parseInt(localStorage.getItem("quizStartTime"));
            const endTime = Date.now();
            const timeTaken = Math.floor((endTime - startTime) / 1000);
            localStorage.setItem("quizTimeTaken", timeTaken);

            localStorage.setItem("quizScore", score);
            localStorage.setItem("totalQuestions", questions.length);
            window.location.href = "score.html";
        }
    });

    nextButton.addEventListener("click", function () {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        }
    });

    prevButton.addEventListener("click", function () {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    });
});
    let currentQuestionIndex = 0;
    let userEmail = "";

    // ✅ Example Question Data (Replace with real API call if needed)
    const questions = [
        {
            id: 1, image1: "images/image 1.1.jpeg", image2: "images/image 1.2.png", image3: "images/image 1.3.png",
            correctAnswer: "Option D",
            options: [
                { label: "Option A", image: "images/image 1.4.png" },
                { label: "Option B", image: "images/image 1.5.png" },
                { label: "Option C", image: "images/image 1.6.png" },
                { label: "Option D", image: "images/image 1.7.png" },
                { label: "Option E", image: "images/image 1.8.png" }
            ]
        },
        {
            id: 2, image1: "images/image2.1.png", image2: "images/image2.2.png", image3: "images/image2.3.png",
            correctAnswer: "Option B",
            options: [
                { label: "Option A", image: "images/image2.4.png" },
                { label: "Option B", image: "images/image2.5.png" },
                { label: "Option C", image: "images/image2.6.png" },
                { label: "Option D", image: "images/image2.7.png" },
                { label: "Option E", image: "images/image2.8.png" }
            ]
        },
        {
            id: 3, image1: "images/image3.1.png", image2: "images/image3.2.png", image3: "images/image3.3.png",
            correctAnswer: "Option B",
            options: [
                { label: "Option A", image: "images/image3.4.png" },
                { label: "Option B", image: "images/image3.5.png" },
                { label: "Option C", image: "images/image3.6.png" },
                { label: "Option D", image: "images/image3.7.png" },
                { label: "Option E", image: "images/image3.8.png" }
            ]
        },
        {
            id: 4, image1: "images/image4.1.png", image2: "images/image4.2.png", image3: "images/image4.3.png",
            correctAnswer: "Option B",
            options: [
                { label: "Option A", image: "images/image4.4.png"},
                { label: "Option B", image: "images/image4.5.png" },
                { label: "Option C", image: "images/image4.6.png" },
                { label: "Option D", image: "images/image4.7.png" },
                { label: "Option E", image: "images/image4.8.png" }
            ]
        },
        {
            id: 5, image1: "images/image5.1.png", image2: "images/image5.2.png", image3: "images/image5.3.png",
            correctAnswer: "Option B",
            options: [
                { label: "Option A", image: "images/image5.4.png" },
                { label: "Option B", image: "images/image5.5.png" },
                { label: "Option C", image: "images/image5.6.png" },
                { label: "Option D", image: "images/image5.7.png" },
                { label: "Option E", image: "images/image5.8.png" }
            ]
        }
    ];

    const questionContainer = document.getElementById("question-container");
    const prevButton = document.getElementById("prevButton");
    const nextButton = document.getElementById("nextButton");
    const tracker = document.getElementById("tracker");

    function loadQuestion() {
        const question = questions[currentQuestionIndex];
        questionContainer.innerHTML = `
            <h3>${question.text}</h3>
            <input type="hidden" id="questionId" value="${question.id}">
            ${question.options.map(opt => 
                `<div><input type="radio" name="option" value="${opt}"> ${opt}</div>`
            ).join("")}
        `;

        updateTracker();
        prevButton.disabled = currentQuestionIndex === 0;
        nextButton.disabled = currentQuestionIndex === questions.length - 1;
    }

    function updateTracker() {
        tracker.innerHTML = questions.map((q, index) => 
            `<span class="badge ${index === currentQuestionIndex ? 'bg-primary' : 'bg-secondary'}">${index + 1}</span>`
        ).join(" ");
    }

    function saveUserDetails() {
        const name = localStorage.getItem("userName");
        const age = localStorage.getItem("userAge");
        userEmail = localStorage.getItem("userEmail");

        if (!name || !age || !userEmail) {
            userEmail = prompt("Enter your email to continue:");
            if (!userEmail) return;

            localStorage.setItem("userName", prompt("Enter your name:"));
            localStorage.setItem("userAge", prompt("Enter your age:"));
            localStorage.setItem("userEmail", userEmail);

            fetch("http://localhost:5000/start-quiz", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name, age, email: userEmail })
            })
            .then(response => response.json())
            .then(data => console.log("✅ User saved:", data))
            .catch(error => console.error("❌ Error:", error));
        }
    }

    document.getElementById("feedbackForm").addEventListener("submit", function (event) {
        event.preventDefault();
        
        const questionId = document.getElementById("questionId").value;
        const selectedOption = document.querySelector('input[name="option"]:checked')?.value;

        if (!selectedOption) {
            alert("Please select an option before saving!");
            return;
        }

        fetch("http://localhost:5000/save-answer", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: userEmail, questionId, selectedOption })
        })
        .then(response => response.json())
        .then(data => console.log("✅ Answer saved:", data))
        .catch(error => console.error("❌ Error:", error));
    });

    document.getElementById("clearResponse").addEventListener("click", function () {
        fetch("http://localhost:5000/clear-responses", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: userEmail })
        })
        .then(response => response.json())
        .then(data => console.log("✅ Responses cleared:", data))
        .catch(error => console.error("❌ Error:", error));
    });

    prevButton.addEventListener("click", function () {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion();
        }
    });

    nextButton.addEventListener("click", function () {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            loadQuestion();
        }
    });

    saveUserDetails();
    loadQuestion();

console.log("Stored Answer:", localStorage.getItem(`question_${question.id}_answer`));

document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await fetch("https://feedbackcollection-5wlz.onrender.com/forms/1/questions");
        const data = await response.json();

        console.log("API Response:", data); // Check structure

        if (!data || !Array.isArray(data)) {
            throw new Error("Invalid data format. Expected an array.");
        }

        const questionsContainer = document.getElementById("questions-container");
        
        data.forEach(entry => {
            const questionElement = document.createElement("p");
            questionElement.textContent = entry.questionText || "No question text found";
            questionsContainer.appendChild(questionElement);
        });
    } catch (error) {
        console.error("Error fetching questions:", error);
    }
});
