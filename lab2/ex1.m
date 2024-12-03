%% Primal problem

% Maximize 13x1 + 11x2
c = [13; 11];

%subject to:
% 4x1 + 5x2 <= 1500
% 5x1 + 3x2 <= 1575
% x1 + 2x2 <= 420

A = [4, 5; 5, 3; 1, 2];
b = [1500; 1575; 420];

% Change sign of c to minimize
c = -c;

% Solve
[~, x] = linprog(c, A, b, [], [], [0, 0]);
x = -x;

%% Dual problem

%Minimize 1500y1 + 1575y2 + 420y3
c_d = [1500; 1575; 420];

%subject to:
% 4y1 + 5y2 + y3 >= 13
% 5y1 + 3y2 + 2y3 >= 11

A_d = [-4, -5, -1; -5, -3, -2];
b_d = [-13; -11];

% Solve
[~, x_d] = linprog(c_d, A_d, b_d, [], [], [0,0,0]);

%% Collect answers
disp('Primal problem: X = 13x1 + 11x2')
disp(['X = ', num2str(x)])

disp('Dual problem: Y = 1500y1 + 1575y2 + 420y3')
disp(['Y = ', num2str(x_d)])
