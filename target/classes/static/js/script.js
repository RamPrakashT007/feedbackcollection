let currentQuestionIndex = 0;
let selectedAnswers = {}; // Store user responses
let questions = []; // Store fetched questions
let startTime; // Start time of the quiz
let timerInterval; // To store the timer interval
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('userId');

if (!userId) {
    console.error("User ID is missing in the URL!");
    // Handle the error (e.g., redirect back to the user details page)
}


document.addEventListener("DOMContentLoaded", function () {
    // Restore startTime from localStorage or initialize it
    if (localStorage.getItem('quizStartTime')) {
        startTime = parseInt(localStorage.getItem('quizStartTime')); // Restore startTime
    } else {
        startTime = Date.now(); // Initialize startTime
        localStorage.setItem('quizStartTime', startTime); // Save startTime to localStorage
    }

    fetchQuestions();
    fetchUserResponses(); // Fetch user's saved responses from the database
    startTimer(); // Start the timer when the page loads
});


const questionContainer = document.getElementById("question-container");
const nextButton = document.getElementById("nextButton");
const prevButton = document.getElementById("prevButton");
const trackerContainer = document.getElementById("tracker");
const submitButton = document.getElementById("submitButton"); // Reference to the Submit button
const timerDisplay = document.createElement("div");
timerDisplay.classList.add("timer", "text-center", "fw-bold", "mt-3");
document.body.prepend(timerDisplay);

// ✅ Function to fetch questions from the backend
function fetchQuestions() {
    fetch("https://feedbackcollection-5wlz.onrender.com/api/questions")
        .then(response => response.json())
        .then(data => {
            questions = data;
            initializeTracker(questions.length);
            loadQuestion(currentQuestionIndex);
            updateTracker();
        })
        .catch(error => console.error("Error fetching questions:", error));
}

// ✅ Fetch user's saved responses from the database
function fetchUserResponses() {
    fetch(`https://feedbackcollection-5wlz.onrender.com/api/user-responses?userId=${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch user responses");
            }
            return response.json();
        })
        .then(data => {
            // Populate selectedAnswers with the fetched responses
            data.forEach(response => {
                selectedAnswers[response.questionId] = response.selectedOption;
            });

            // Fetch the saved current question index
            fetch(`https://feedbackcollection-5wlz.onrender.com/users/${userId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Failed to fetch current question index");
                    }
                    return response.json();
                })
                .then(user => {
                    currentQuestionIndex = user.currentQuestionIndex; // Set the current question index
                    loadQuestion(currentQuestionIndex); // Load the saved question
                })
                .catch(error => console.error("Error fetching current question index:", error));
        })
        .catch(error => console.error("Error fetching user responses:", error));
}

function loadQuestion(index) {
    if (questions.length === 0) {
        console.error("Questions not loaded yet!");
        return;
    }

    const question = questions[index];
    const questionId = question.id;

    let questionHTML = `
        <h2>Q.No.${question.id}</h2>
        <div class="question">
            <p><strong></strong> Visualize the rotation of the 3D object.</p>
            <div class="d-flex justify-content-center align-items-center gap-4">
                <img src="${question.image1}" class="img-fluid" alt="Example 1" style="max-width: 150px;">
                <h3>Is Rotated To</h3>
                <img src="${question.image2}" class="img-fluid" alt="Example 2" style="max-width: 150px;">
            </div>
            <h3 class="mt-3">As</h3>
            <img src="${question.image3}" class="img-fluid" alt="Example 3" style="max-width: 150px;">
            <h3>is Rotated To</h3>
            <div class="options d-flex justify-content-center gap-4 flex-wrap">`;

    question.options.forEach((option) => {
        const isChecked = selectedAnswers[questionId] === option.label ? "checked" : "";
        questionHTML += `
            <label class="text-center">
                <input type="radio" name="question" value="${option.label}" ${isChecked} 
                       onclick="selectOption(${questionId}, '${option.label}')">
                <br>
                <img src="${option.image}" class="img-fluid" alt="${option.label}" style="max-width: 120px;">
                <br>
                ${option.label}
            </label>`;
    });

    // Add the Clear Response and Save buttons
    questionHTML += `
            <div class="mt-3 w-100 text-center">
                <button type="button" id="clearButton" class="btn btn-warning">Clear Response</button>
                <button type="button" id="saveButton" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>`;

    questionContainer.innerHTML = questionHTML;

    // Add event listener for the Clear Response button
    document.getElementById("clearButton").addEventListener("click", function () {
        const questionId = questions[currentQuestionIndex].id;

        // Call the DELETE API to clear the response
        fetch(`https://feedbackcollection-5wlz.onrender.com/api/clear-response?userId=${userId}&questionId=${questionId}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                // Clear the response locally
                delete selectedAnswers[questionId];

                // Uncheck the selected radio button
                document.querySelectorAll('input[name="question"]').forEach(input => {
                    if (input.checked) {
                        input.checked = false;
                    }
                });

                // Re-enable the Save button
                document.getElementById("saveButton").disabled = false;
                updateTracker();

            } else {
                console.error("Failed to clear response");
            }
        })
        .catch(error => console.error("Error clearing response:", error));
    });


    // Add event listener for the Save button
    const saveButton = document.getElementById("saveButton");
    saveButton.addEventListener("click", function () {
        const questionId = questions[currentQuestionIndex].id;
        const selectedOption = selectedAnswers[questionId];
        const correctAnswer = questions[currentQuestionIndex].correctAnswer;
        const timeTaken = Math.floor((Date.now() - startTime) / 1000); // Time in seconds

        if (selectedOption) {
            saveResponse(questionId, selectedOption, correctAnswer, timeTaken);
            saveButton.disabled = true; // Disable the Save button after saving
            updateTracker();
        } else {
            alert("Please select an option before saving.");
        }
    });

    // Update the Next button
    if (selectedAnswers[questionId]) {
        saveButton.disabled = true;
    } else {
        saveButton.disabled = false;
    }

    // Update the Next button
    if (currentQuestionIndex === questions.length - 1) {
        nextButton.disabled = true; // Freeze the Next button on the last question
    } else {
        nextButton.disabled = false; // Enable the Next button for other questions
    }

    // Enable/disable Previous button
    document.getElementById("prevButton").disabled = index === 0;

    // Enable/disable Submit button based on whether all questions are answered
    const allQuestionsAnswered = Object.keys(selectedAnswers).length === questions.length;
    submitButton.disabled = !allQuestionsAnswered;

    // Attach the event listener to the Next button
    nextButton.removeEventListener("click", handleNextButtonClick); // Remove previous listener
    nextButton.addEventListener("click", handleNextButtonClick); // Add new listener
}

// ✅ Handle next button click
function handleNextButtonClick() {
    if (currentQuestionIndex < questions.length - 1) {
        currentQuestionIndex++;
        saveCurrentQuestionIndex(currentQuestionIndex); // Save the current question index
        loadQuestion(currentQuestionIndex);
    }
}

function handlePrevButtonClick() {
    if (currentQuestionIndex > 0) {
        currentQuestionIndex--;
        saveCurrentQuestionIndex(currentQuestionIndex); // Save the current question index
        loadQuestion(currentQuestionIndex);
    }
}

function saveCurrentQuestionIndex(currentQuestionIndex) {
    fetch("https://feedbackcollection-5wlz.onrender.com/users/update-current-question-index", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            userId: userId,
            currentQuestionIndex: currentQuestionIndex
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to save current question index");
        }
        return response.json(); // Parse the response as JSON
    })
    .then(data => {
        console.log("Current question index saved successfully:", data.message);
    })
    .catch(error => {
        console.error("Error saving current question index:", error);
    });
}

// Attach the event listener to the Previous button
prevButton.addEventListener("click", handlePrevButtonClick);

// Attach the event listener to the Submit button
submitButton.addEventListener("click", handleSubmitButtonClick);
async function handleSubmitButtonClick() {
    const totalTimeTaken = Math.floor((Date.now() - startTime) / 1000); // Time in seconds
    const totalScore = Object.values(selectedAnswers).reduce((score, answer, index) =>
        score + (answer === questions[index]?.correctAnswer ? 1 : 0), 0);

    console.log("Total Score:", totalScore);
    console.log("Total Time Taken:", totalTimeTaken);

    try {
        const response = await fetch("/api/save-quiz-summary", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                userId: userId,
                totalScore: totalScore,
                totalTimeTaken: totalTimeTaken
            })
        });

        console.log("Response status:", response.status);

        if (!response.ok) {
            const errorData = await response.json();
            console.error("Error response data:", errorData);
            throw new Error("Failed to save quiz summary");
        }

        const data = await response.json();
        console.log("Quiz summary saved successfully:", data);

        // Clear the timer from localStorage
        localStorage.removeItem('quizStartTime');

        // Redirect to the score page with query parameters
        const redirectUrl = `/score?userId=${userId}&totalScore=${totalScore}&totalTimeTaken=${totalTimeTaken}&quizSummaryId=${data.quizSummaryId}`;
        window.location.href = redirectUrl;
    } catch (error) {
        console.error("Error saving quiz summary:", error);
        alert("Failed to save quiz results. Please try again.");
    }
}

// ✅ Select an option
function selectOption(questionId, selectedOption) {
    selectedAnswers[questionId] = selectedOption;

    // Enable the Submit button if all questions are answered
    const allQuestionsAnswered = Object.keys(selectedAnswers).length === questions.length;
    submitButton.disabled = !allQuestionsAnswered;

    console.log("Selected Answers:", selectedAnswers);
    console.log("Submit Button Disabled:", submitButton.disabled);

    updateTracker(); // Update the tracker to reflect the selected answer
}

// ✅ Save response to backend
function saveResponse(questionId, selectedOption, correctAnswer, timeTaken) {
    fetch(`https://feedbackcollection-5wlz.onrender.com/api/save-response?userId=${userId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            questionId: questionId,
            selectedOption: selectedOption,
            correctAnswer: correctAnswer,
            timeTaken: timeTaken
        })
    })
    .catch(error => {
        console.error("Error saving response:", error);
        alert("Failed to save response. Please try again.");
    });
}

// ✅ Timer functionality
function startTimer() {
    timerInterval = setInterval(updateTimer, 1000);
}

function updateTimer() {
    const currentTime = Date.now();
    const elapsedTime = Math.floor((currentTime - startTime) / 1000); // Time in seconds
    const minutes = Math.floor(elapsedTime / 60);
    const seconds = elapsedTime % 60;
    timerDisplay.innerHTML = `⏳ Time Elapsed: <strong>${minutes} min ${seconds} sec</strong>`;
}

function initializeTracker(totalQuestions) {
    const trackerContainer = document.getElementById("tracker");
    trackerContainer.innerHTML = ""; // Clear existing content

    for (let i = 1; i <= totalQuestions; i++) {
        const trackerBox = document.createElement("div");
        trackerBox.classList.add("tracker-box", "unanswered");
        trackerBox.textContent = i;
        trackerBox.addEventListener("click", () => goToQuestion(i - 1)); // Navigate to the question
        trackerContainer.appendChild(trackerBox);
    }
}

function updateTracker() {
    const trackerBoxes = document.querySelectorAll(".tracker-box");

    trackerBoxes.forEach((box, index) => {
        const questionId = questions[index].id;
        if (selectedAnswers[questionId]) {
            box.classList.remove("unanswered");
            box.classList.add("answered");
        } else {
            box.classList.remove("answered");
            box.classList.add("unanswered");
        }
    });
}

function goToQuestion(index) {
    currentQuestionIndex = index;
    loadQuestion(currentQuestionIndex);
}