#!/bin/bash

# Make sure we're in the correct Git repository folder
echo "Please navigate to your Git repository folder first!"

# Get the current directory
current_dir=$(pwd)

# Check if it's a Git repository
if [ ! -d "$current_dir/.git" ]; then
  echo "This is not a Git repository. Exiting..."
  exit 1
fi

# Ask for commit message
echo "Enter commit message: "
read commit_message

# Stage changes
echo "Staging changes..."
git add .

# Commit changes
echo "Committing changes with message: '$commit_message'"
git commit -m "$commit_message"

# Push changes to the main branch (or replace with your branch name)
echo "Pushing changes to GitHub..."
git push origin main

# Output success message
echo "Changes pushed successfully to GitHub."
