%% Positive integers
A = [
    1, 0, 0, 1, 1, 1, 1; 
    1, 1, 0, 0, 1, 1, 1; 
    1, 1, 1, 0, 0, 1, 1; 
    1, 1, 1, 1, 0, 0, 1; 
    1, 1, 1, 1, 1, 0, 0; 
    0, 1, 1, 1, 1, 1, 0; 
    0, 0, 1, 1, 1, 1, 1; 
];

b = [8; 6; 5; 4; 6; 7; 9;];

c = ones(7, 1);

intcon = 1:7;

[~, x] = intlinprog(c, intcon, -A, -b, [], [], zeros(7, 1));

%% Relaxed problem
A = [
    1, 0, 0, 1, 1, 1, 1; 
    1, 1, 0, 0, 1, 1, 1; 
    1, 1, 1, 0, 0, 1, 1; 
    1, 1, 1, 1, 0, 0, 1; 
    1, 1, 1, 1, 1, 0, 0; 
    0, 1, 1, 1, 1, 1, 0; 
    0, 0, 1, 1, 1, 1, 1; 
];

b = [8; 6; 5; 4; 6; 7; 9;];

c = ones(7, 1);

[~, x_r] = linprog(c, -A, -b, [], [], zeros(7, 1));

%% Collect Results
disp('Positive integers')
disp(x)
disp('Relaxed problem')
disp(x_r)