let currentStep = 0;    // Current step is set to be 0

const steps = document.getElementById('steps');
const stepCount = steps.children.length;
const maxStep = stepCount - 1;

function showStep(step) {
    steps.style.transform = `translateX(-${step * 100}%)`;

    document.getElementById('prevBtn').classList.toggle('hidden', step === 0);
    document.getElementById('nextBtn').innerText = step === maxStep ? 'Submit' : 'Tiếp theo';
}

function nextStep() {
    if (currentStep < maxStep) {
        currentStep++;
        showStep(currentStep);
    } else if(currentStep === maxStep) {
        const form = document.getElementById('form');
        form.submit();
    }
}

function prevStep() {
    if (currentStep > 0) {
        currentStep--;
        showStep(currentStep);
    }
}