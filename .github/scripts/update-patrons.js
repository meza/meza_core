const fs = require('fs');
const { readFile, writeFile } = fs.promises;

const README_PATH = 'README.md';
const START_MARKER = '<!-- marker:patrons-start -->';
const END_MARKER = '<!-- marker:patrons-end -->';
const JSON_URL = 'https://vsbmeza3.com/supporters.json';

async function main() {
    const readme = await readFile(README_PATH, 'utf8');

    if (!readme.includes(START_MARKER) || !readme.includes(END_MARKER)) {
        console.log('Missing patron markers in README.md.');
        process.exit(0);
    }

    let data;
    try {
        const res = await fetch(JSON_URL);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        data = await res.json();
    } catch (err) {
        console.error(`Failed to fetch supporter data from ${JSON_URL}:`, err.message);
        process.exit(1);
    }

    if (!Array.isArray(data?.tiers) || data.tiers.length === 0) {
        console.error('Supporter data is missing or malformed.');
        process.exit(1);
    }
    data.tiers.push({ members: [{name: "test"}, {name: "two"}, {name: "three"}, {name: "four"}] });
    const lastTier = data.tiers[data.tiers.length - 1];
    const names = lastTier.members.map(m => m.name).join(' · ');
    const replacement = `${START_MARKER}\n\n${names}\n\n${END_MARKER}`;

    const pattern = new RegExp(`${START_MARKER}[\\s\\S]*?${END_MARKER}`);
    const updated = readme.replace(pattern, replacement);

    await writeFile(README_PATH, updated);
    console.log('Updated README with supporter names from last tier.');
}

main();
