# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog].

## [v4.1.4-1.19.2] - 2022-10-22
### Added
- Added an alternative overlay for the next item to be taken from a bag on right-clicking (available in the config)
### Changed
- When extracting an item from a bag in an inventory menu via right-clicking the last item is no longer the one taken out first. Instead, use the scroll wheel to choose which item you want to take from the bag.
- White- and blacklists are now controllable per bag type

## [v4.1.3-1.19.2] - 2022-10-19
### Added
- Added a `+` indicator that is shown on bags when the stack carried by the cursor can be added to them in your inventory
- Added translation for `zh_cn` by radical233, thanks!

## [v4.1.2-1.19.2] - 2022-09-14
### Added
- Added a whitelist for items allowed in bags, no other items will be permitted in bags when the whitelist has at least one entry

## [v4.1.1-1.19.2] - 2022-09-01
- Recompile for Puzzles Lib v4.3.0

## [v4.1.0-1.19.2] - 2022-08-21
- Compiled for Minecraft 1.19.2

## [v4.0.0-1.19.1] - 2022-08-03
- Ported to Minecraft 1.19.1
- Split into multi-loader project

[Keep a Changelog]: https://keepachangelog.com/en/1.0.0/
